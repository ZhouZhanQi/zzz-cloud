package com.zzz.auth.provider.support.sms;

import com.zzz.auth.provider.support.customize.ZzzOauth2AuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.Map;

/**
 * @author: zhouzq
 * @date: 2022/7/12-11:02
 * @desc: 密码授权处理
 */
public class OAuth2ResourceOwnerSmsAuthenticationProvider extends ZzzOauth2AuthenticationProvider<OAuth2ResourceOwnerSmsAuthenticationToken> {

    public OAuth2ResourceOwnerSmsAuthenticationProvider(AuthenticationManager authenticationManager, OAuth2AuthorizationService authorizationService, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        super(authenticationManager, authorizationService, tokenGenerator);
    }

    @Override
    public UsernamePasswordAuthenticationToken buildToken(Map<String, Object> requestParameters) {
        return null;
    }

    @Override
    public void checkClient(RegisteredClient registeredClient) {

    }
}
