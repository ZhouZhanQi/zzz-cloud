package com.zzz.oauth.provider.authorization;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zzz.framework.common.util.AssertUtils;
import com.zzz.oauth.api.model.constants.ZzzOauthConstant;
import com.zzz.oauth.api.model.domain.SysClient;
import com.zzz.oauth.provider.service.impl.SysClientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Optional;

/**
 * @author zhouzq
 */
@RequiredArgsConstructor
public class ZzzRegisteredClientServiceImpl implements RegisteredClientRepository {

    private final SysClientServiceImpl sysClientService;


    /**
     * 刷新令牌有效期默认 30 天
     */
    private final static int refreshTokenValiditySeconds = 60 * 60 * 24 * 30;

    /**
     * 请求令牌有效期默认 12 小时
     */
    private final static int accessTokenValiditySeconds = 60 * 60 * 12;

    @Override
    public void save(RegisteredClient registeredClient) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegisteredClient findById(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Wrapper<SysClient> queryWrapper = Wrappers.lambdaQuery(SysClient.class)
                .eq(SysClient::getClientId, clientId);
        SysClient sysClient = sysClientService.getOne(queryWrapper);
        AssertUtils.checkNotNull(sysClient, "");
        RegisteredClient.Builder builder = RegisteredClient.withId(sysClient.getClientId())
                .clientId(sysClient.getClientId())
                .clientSecret(ZzzOauthConstant.NOOP + sysClient.getClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
        // 授权模式
        Optional.ofNullable(sysClient.getAuthorizedGrantTypes())
                .ifPresent(grants -> StringUtils.commaDelimitedListToSet(grants)
                        .forEach(s -> builder.authorizationGrantType(new AuthorizationGrantType(s))));
        // 回调地址
        Optional.ofNullable(sysClient.getRedirectUri()).ifPresent(builder::redirectUri);
        // scope
        Optional.ofNullable(sysClient.getScope()).ifPresent(builder::scope);
        return builder.tokenSettings(TokenSettings.builder().accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                        .accessTokenTimeToLive(Duration.ofSeconds(Optional.ofNullable(sysClient.getAccessTokenValidity()).orElse(accessTokenValiditySeconds)))
                        .refreshTokenTimeToLive(Duration.ofSeconds(Optional.ofNullable(sysClient.getRefreshTokenValidity()).orElse(refreshTokenValiditySeconds)))
                        .build())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(!sysClient.getAutoapprove()).build())
                .build();
    }
}
