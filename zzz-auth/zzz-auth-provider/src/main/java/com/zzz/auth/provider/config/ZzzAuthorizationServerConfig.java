package com.zzz.auth.provider.config;

import com.google.common.collect.Lists;
import com.zzz.auth.provider.support.ZzzOauth2TokenGenerate;
import com.zzz.auth.provider.support.base.ZzzAuthenticationProvider;
import com.zzz.auth.provider.support.base.ZzzOAuth2TokenCustomizer;
import com.zzz.auth.provider.support.handler.ZzzAuthFailHandler;
import com.zzz.auth.provider.support.handler.ZzzAuthSuccessHandler;
import com.zzz.auth.provider.support.pwd.OAuth2ResourceOwnerPwdAuthenticationConvert;
import com.zzz.auth.provider.support.pwd.OAuth2ResourceOwnerPwdAuthenticationProvider;
import com.zzz.auth.provider.support.sms.OAuth2ResourceOwnerSmsAuthenticationConvert;
import com.zzz.auth.provider.support.sms.OAuth2ResourceOwnerSmsAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.DelegatingAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeRequestAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author: zhouzq
 * @date: 2022/7/11-15:57
 * @desc:
 */
@Configuration
@RequiredArgsConstructor
public class ZzzAuthorizationServerConfig {

    private final OAuth2AuthorizationService oAuth2AuthorizationService;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @SneakyThrows
    public SecurityFilterChain authSecurityFilterChain(HttpSecurity http) {
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer<>();
        http.apply(authorizationServerConfigurer.tokenEndpoint((tokenEndpoint) -> {// 个性化认证授权端点
                    tokenEndpoint.accessTokenRequestConverter(accessTokenRequestConverter()) // 注入自定义的授权认证Converter
                            .accessTokenResponseHandler(new ZzzAuthSuccessHandler()) // 登录成功处理器
                            .errorResponseHandler(new ZzzAuthFailHandler());// 登录失败处理器
                }).clientAuthentication(oAuth2ClientAuthenticationConfigurer -> // 个性化客户端认证
                        oAuth2ClientAuthenticationConfigurer.errorResponseHandler(new ZzzAuthFailHandler()))// 处理客户端认证异常
                );
        DefaultSecurityFilterChain securityFilterChain = http.requestMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .apply(authorizationServerConfigurer.authorizationService(oAuth2AuthorizationService)// redis存储token的实现
                        .providerSettings(ProviderSettings.builder().build()))
                .and().build();

        // 注入自定义授权模式实现
        addCustomOAuth2GrantAuthenticationProvider(http);
        return securityFilterChain;
    }


    /**
     * 自定义转换器并注入委派转换器
     * @return
     */
    private AuthenticationConverter accessTokenRequestConverter() {
        return new DelegatingAuthenticationConverter(Lists.newArrayList(
                new OAuth2ResourceOwnerPwdAuthenticationConvert(),
                new OAuth2ResourceOwnerSmsAuthenticationConvert(), new OAuth2RefreshTokenAuthenticationConverter(),
                new OAuth2ClientCredentialsAuthenticationConverter(),
                new OAuth2AuthorizationCodeAuthenticationConverter(),
                new OAuth2AuthorizationCodeRequestAuthenticationConverter()));
    }

    /**
     * 注入授权模式实现提供方
     * 1. 密码模式
     * 2. 短信登录
     */
    @SuppressWarnings("unchecked")
    private void addCustomOAuth2GrantAuthenticationProvider(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);

        OAuth2ResourceOwnerPwdAuthenticationProvider pwdAuthenticationProvider = new OAuth2ResourceOwnerPwdAuthenticationProvider(
                authenticationManager, authorizationService, oAuth2TokenGenerator());

        OAuth2ResourceOwnerSmsAuthenticationProvider smsAuthenticationProvider = new OAuth2ResourceOwnerSmsAuthenticationProvider(
                authenticationManager, authorizationService, oAuth2TokenGenerator());

        // 处理 UsernamePasswordAuthenticationToken
        http.authenticationProvider(new ZzzAuthenticationProvider());
        // 处理 OAuth2ResourceOwnerPwdAuthenticationToken
        http.authenticationProvider(pwdAuthenticationProvider);
        // 处理 OAuth2ResourceOwnerSmsAuthenticationToken
        http.authenticationProvider(smsAuthenticationProvider);
    }

    @Bean
    public OAuth2TokenGenerator oAuth2TokenGenerator() {
        ZzzOauth2TokenGenerate accessTokenGenerator = new ZzzOauth2TokenGenerate();
        // 注入Token 扩展关联用户信息
        accessTokenGenerator.setAccessTokenCustomizer(new ZzzOAuth2TokenCustomizer());
        return new DelegatingOAuth2TokenGenerator(accessTokenGenerator, new OAuth2RefreshTokenGenerator());
    }
}
