package com.zzz.auth.provider.support;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.zzz.framework.common.util.AssertUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author: zhouzq
 * @date: 2022/7/11-18:41
 * @desc: oauth2 token生成器
 */
public class ZzzOauth2TokenGenerate implements OAuth2TokenGenerator<OAuth2AccessToken> {

    private OAuth2TokenCustomizer<OAuth2TokenClaimsContext> oAuth2TokenCustomizer;

    @Override
    public OAuth2AccessToken generate(OAuth2TokenContext context) {
        if (!OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())
                || !OAuth2TokenFormat.REFERENCE.equals(context.getRegisteredClient().getTokenSettings().getAccessTokenFormat())) {
            return null;
        }

        //发布者信息
        String issuer = Optional.ofNullable(context.getAuthorizationServerContext()).map(providerContext -> providerContext.getIssuer()).orElse(null);

        RegisteredClient registeredClient = context.getRegisteredClient();
        //发布时间
        Instant issuedAt = Instant.now();
        //过期时间
        Instant expiresAt = issuedAt.plus(registeredClient.getTokenSettings().getAccessTokenTimeToLive());

        // @formatter:off
        OAuth2TokenClaimsSet.Builder claimsBuilder = OAuth2TokenClaimsSet.builder();
        if (!StrUtil.isEmpty(issuer)) {
            claimsBuilder.issuer(issuer);
        }
        claimsBuilder.subject(context.getPrincipal().getName())
                .audience(Collections.singletonList(registeredClient.getClientId()))
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .notBefore(issuedAt)
                .id(UUID.fastUUID().toString());
        if (!CollectionUtils.isEmpty(context.getAuthorizedScopes())) {
            claimsBuilder.claim(OAuth2ParameterNames.SCOPE, context.getAuthorizedScopes());
        }
        // @formatter:on
        if (Objects.nonNull(this.oAuth2TokenCustomizer)) {
            // @formatter:off
            OAuth2TokenClaimsContext.Builder accessTokenContextBuilder = OAuth2TokenClaimsContext.with(claimsBuilder)
                    .registeredClient(context.getRegisteredClient())
                    .principal(context.getPrincipal())
                    .authorizationServerContext(context.getAuthorizationServerContext())
                    .authorizedScopes(context.getAuthorizedScopes())
                    .tokenType(context.getTokenType())
                    .authorizationGrantType(context.getAuthorizationGrantType());
            Optional.ofNullable(context.getAuthorization()).ifPresent(accessTokenContextBuilder::authorization);
            Optional.ofNullable(context.getAuthorizationGrant()).ifPresent(authorizationGrant -> accessTokenContextBuilder.authorizationGrant((Authentication) authorizationGrant));
            // @formatter:on
            this.oAuth2TokenCustomizer.customize(accessTokenContextBuilder.build());
        }

        OAuth2TokenClaimsSet accessTokenClaimsSet = claimsBuilder.build();
        return new ZzzOauth2TokenGenerate.OAuth2AccessTokenClaims(OAuth2AccessToken.TokenType.BEARER, UUID.fastUUID().toString(),
                accessTokenClaimsSet.getIssuedAt(), accessTokenClaimsSet.getExpiresAt(), context.getAuthorizedScopes(),
                accessTokenClaimsSet.getClaims());
    }

    /**
     * Sets the {@link OAuth2TokenCustomizer} that customizes the
     * {@link OAuth2TokenClaimsContext#getClaims() claims} for the
     * {@link OAuth2AccessToken}.
     * @param oAuth2TokenCustomizer the {@link OAuth2TokenCustomizer} that customizes the
     * claims for the {@code OAuth2AccessToken}
     */
    public void setAccessTokenCustomizer(OAuth2TokenCustomizer<OAuth2TokenClaimsContext> oAuth2TokenCustomizer) {
        AssertUtils.checkNotNull(oAuth2TokenCustomizer, "oAuth2TokenCustomizer cannot be null");
        this.oAuth2TokenCustomizer = oAuth2TokenCustomizer;
    }

    private static final class OAuth2AccessTokenClaims extends OAuth2AccessToken implements ClaimAccessor {
        private final Map<String, Object> claims;

        private OAuth2AccessTokenClaims(TokenType tokenType, String tokenValue, Instant issuedAt, Instant expiresAt,
                                        Set<String> scopes, Map<String, Object> claims) {
            super(tokenType, tokenValue, issuedAt, expiresAt, scopes);
            this.claims = claims;
        }

        @Override
        public Map<String, Object> getClaims() {
            return this.claims;
        }
    }
}
