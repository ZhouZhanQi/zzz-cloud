package com.zzz.auth.provider.support.customize;

import com.zzz.auth.api.model.code.AuthResponseCode;
import com.zzz.framework.common.util.AssertUtils;
import com.zzz.framework.starter.cache.RedisCacheHelper;
import com.zzz.framework.starter.core.model.ZzzUser;
import com.zzz.framework.starter.core.model.enums.CommonKeyPrefix;
import com.zzz.framework.starter.security.model.bo.ZzzUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhouzq
 * @date: 2022/7/12-11:06
 * @desc: 自定义授权
 */
@Slf4j
public abstract class ZzzOauth2AuthenticationProvider<T extends ZzzOauth2AuthenticationToken> implements AuthenticationProvider {

    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-4.1.2.1";


    private final OAuth2AuthorizationService authorizationService;

    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    private final AuthenticationManager authenticationManager;


    private final RedisCacheHelper<ZzzUser> redisCacheHelper;

    public ZzzOauth2AuthenticationProvider(AuthenticationManager authenticationManager,
                                           OAuth2AuthorizationService authorizationService,
                                           OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
                                           RedisCacheHelper<ZzzUser> redisCacheHelper) {
        AssertUtils.checkNotNull(authorizationService, AuthResponseCode.AUTHENTICATION_SERVICE_IS_NULL);
        AssertUtils.checkNotNull(tokenGenerator, AuthResponseCode.TOKEN_GENERATOR_IS_NULL);
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
        this.redisCacheHelper = redisCacheHelper;
    }

    /**
     *
     * @param authentication the authentication request object.
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        T resourceOwnerBaseAuthentication = (T) authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = getAuthenticatedClientElseThrowInvalidClient(resourceOwnerBaseAuthentication);

        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        //todo 校验客户端
        checkClient(registeredClient);

        AssertUtils.checkArgument(CollectionUtils.isEmpty(resourceOwnerBaseAuthentication.getScopes()), AuthResponseCode.AUTHENTICATION_SCOPE_IS_EMPTY);

        // Default to configured scopes
        resourceOwnerBaseAuthentication.getScopes().stream().forEach(requestedScope -> {
            AssertUtils.checkArgument(!registeredClient.getScopes().contains(requestedScope), new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE));
        });
        Set<String> authorizedScopes = new LinkedHashSet<>(resourceOwnerBaseAuthentication.getScopes());

        Map<String, Object> requestParameters = resourceOwnerBaseAuthentication.getAdditionalParameters();
        try {
            //build authentication token
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = buildToken(requestParameters);

            if (log.isDebugEnabled()) {
                log.debug(">>> oauth2 authentication provider username password authentication token: {}", usernamePasswordAuthenticationToken);
            }

            //获取全部认证信息
            Authentication usernamePasswordAuthentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            // @formatter:off
            DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                    .registeredClient(registeredClient)
                    .principal(usernamePasswordAuthentication)
                    .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                    .authorizedScopes(authorizedScopes)
                    .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                    .authorizationGrant(resourceOwnerBaseAuthentication);
            // @formatter:on

            OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization
                    .withRegisteredClient(registeredClient).principalName(usernamePasswordAuthentication.getName())
                    .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                    .authorizedScopes(authorizedScopes);

            // ----- Access token -----
            OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
            OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
            Optional.ofNullable(generatedAccessToken).orElseThrow(() -> new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, "oauth2 token generated failed", ERROR_URI)));

            OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                    generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                    generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());

            if (generatedAccessToken instanceof ClaimAccessor claimAccessor) {
                authorizationBuilder.id(accessToken.getTokenValue())
                        .token(accessToken, (metadata) -> metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, claimAccessor.getClaims()))
                        .authorizedScopes(authorizedScopes)
                        .attribute(Principal.class.getName(), usernamePasswordAuthentication);
            } else {
                authorizationBuilder.id(accessToken.getTokenValue()).accessToken(accessToken);
            }

            // ----- Refresh token -----
            OAuth2RefreshToken refreshToken = null;
            if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
                    // Do not issue refresh token to public client
                    !clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {

                tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
                OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
                if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                    OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                            "The token generator failed to generate the refresh token.", ERROR_URI);
                    throw new OAuth2AuthenticationException(error);
                }
                refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
                authorizationBuilder.refreshToken(refreshToken);
            }

            OAuth2Authorization authorization = authorizationBuilder.build();
            this.authorizationService.save(authorization);

            //afterauth会删除此缓存
            ZzzUserDetail userDetail = (ZzzUserDetail)usernamePasswordAuthentication.getPrincipal();
            redisCacheHelper.set(CommonKeyPrefix.ZZZ_USER_INFO, accessToken.getTokenValue(), userDetail.getZzzUser(), 3, TimeUnit.HOURS);
            return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken,
                    refreshToken, Objects.requireNonNull(authorization.getAccessToken().getClaims()));
        } catch (Exception e) {
            log.error(">>> authenticate error: {}", e.getMessage(), e);
            throw oAuth2AuthenticationException(authentication, (AuthenticationException) e);
        }
    }

    /**
     * 当前客户端是否支持此模式
     * @param registeredClient
     */
    public abstract void checkClient(RegisteredClient registeredClient);

    /**
     * 构造账户密码认证令牌
     * @param requestParameters
     * @return
     */
    public abstract UsernamePasswordAuthenticationToken buildToken(Map<String, Object> requestParameters);

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

    private OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(
            Authentication authentication) {
        OAuth2ClientAuthenticationToken clientPrincipal = null;
        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
            clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();
        }
        if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
            return clientPrincipal;
        }
        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
    }


    /**
     * 登录异常转换为oauth2异常
     * @param authentication 身份验证
     * @param authenticationException 身份验证异常
     * @return {@link OAuth2AuthenticationException}
     */
    private OAuth2AuthenticationException oAuth2AuthenticationException(Authentication authentication,
                                                                        AuthenticationException authenticationException) {

        return new OAuth2AuthenticationException("");
    }

}
