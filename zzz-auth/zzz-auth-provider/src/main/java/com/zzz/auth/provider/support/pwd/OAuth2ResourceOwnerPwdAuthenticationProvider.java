package com.zzz.auth.provider.support.pwd;

import com.zzz.auth.provider.support.customize.ZzzOauth2AuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.Map;

/**
 * @author: zhouzq
 * @date: 2022/7/12-11:02
 * @desc: 密码授权处理
 */
@Slf4j
public class OAuth2ResourceOwnerPwdAuthenticationProvider extends ZzzOauth2AuthenticationProvider<OAuth2ResourceOwnerPwdAuthenticationToken> {

    public OAuth2ResourceOwnerPwdAuthenticationProvider(AuthenticationManager authenticationManager, OAuth2AuthorizationService oAuth2AuthorizationService, OAuth2TokenGenerator<OAuth2AccessToken> oAuth2TokenGenerator) {
        super(authenticationManager, oAuth2AuthorizationService, oAuth2TokenGenerator);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean supports = OAuth2ResourceOwnerPwdAuthenticationToken.class.isAssignableFrom(authentication);
        if (log.isDebugEnabled()) {
            log.debug(">>> oauth2 resource owner pwd authentication: {}, supports: {}", authentication, supports);
        }
        return supports;
    }

    @Override
    public void checkClient(RegisteredClient registeredClient) {

    }

    @Override
    public UsernamePasswordAuthenticationToken buildToken(Map<String, Object> requestParameters) {
        String username = (String) requestParameters.get(OAuth2ParameterNames.USERNAME);
        String password = (String) requestParameters.get(OAuth2ParameterNames.PASSWORD);
        return new UsernamePasswordAuthenticationToken(username, password);
    }


    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
