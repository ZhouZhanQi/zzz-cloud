package com.zzz.auth.provider.config;

import cn.hutool.crypto.SecureUtil;
import com.google.common.collect.Lists;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
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
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

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
        http.apply(authorizationServerConfigurer.tokenEndpoint((tokenEndpoint) -> {// ???????????????????????????
                    tokenEndpoint.accessTokenRequestConverter(accessTokenRequestConverter()) // ??????????????????????????????Converter
                            .accessTokenResponseHandler(new ZzzAuthSuccessHandler()) // ?????????????????????
                            .errorResponseHandler(new ZzzAuthFailHandler());// ?????????????????????
                }).clientAuthentication(oAuth2ClientAuthenticationConfigurer -> // ????????????????????????
                        oAuth2ClientAuthenticationConfigurer
                                .errorResponseHandler(new ZzzAuthFailHandler()))
                                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint.consentPage("/oauth2/consent"))// ???????????????????????????
                );

        DefaultSecurityFilterChain securityFilterChain = http.requestMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .apply(authorizationServerConfigurer.authorizationService(oAuth2AuthorizationService)// redis??????token?????????
                        .providerSettings(ProviderSettings.builder().build()))
                .and().formLogin(Customizer.withDefaults()).build();
        addCustomOAuth2GrantAuthenticationProvider(http);
        return securityFilterChain;
    }


    /**
     * ??????????????????????????????????????????
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
     * ?????????????????????????????????
     * 1. ????????????
     * 2. ????????????
     */
    @SuppressWarnings("unchecked")
    private void addCustomOAuth2GrantAuthenticationProvider(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);

        OAuth2ResourceOwnerPwdAuthenticationProvider pwdAuthenticationProvider = new OAuth2ResourceOwnerPwdAuthenticationProvider(
                authenticationManager, authorizationService, oAuth2TokenGenerator());

        OAuth2ResourceOwnerSmsAuthenticationProvider smsAuthenticationProvider = new OAuth2ResourceOwnerSmsAuthenticationProvider(
                authenticationManager, authorizationService, oAuth2TokenGenerator());

        // ?????? UsernamePasswordAuthenticationToken
        http.authenticationProvider(new ZzzAuthenticationProvider());
        // ?????? OAuth2ResourceOwnerPwdAuthenticationToken
        http.authenticationProvider(pwdAuthenticationProvider);
        // ?????? OAuth2ResourceOwnerSmsAuthenticationToken
        http.authenticationProvider(smsAuthenticationProvider);
    }

    @Bean
    public OAuth2TokenGenerator oAuth2TokenGenerator() {
        ZzzOauth2TokenGenerate accessTokenGenerator = new ZzzOauth2TokenGenerate();
        // ??????Token ????????????????????????
        accessTokenGenerator.setAccessTokenCustomizer(new ZzzOAuth2TokenCustomizer());
        return new DelegatingOAuth2TokenGenerator(accessTokenGenerator, new OAuth2RefreshTokenGenerator());
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = SecureUtil.generateKeyPair("RSA", 2048);
        RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
