package com.zzz.auth.provider.support.pwd;

import com.zzz.auth.provider.support.customize.ZzzOauth2AuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * @author: zhouzq
 * @date: 2022/7/12-11:02
 * @desc: 密码授权处理
 */
@RequiredArgsConstructor
public class OAuth2ResourceOwnerPwdAuthenticationProvider extends ZzzOauth2AuthenticationProvider<OAuth2ResourceOwnerPwdAuthenticationToken> {

    private final AuthenticationManager authenticationManager;

    private final OAuth2AuthorizationService oAuth2AuthorizationService;

    private final OAuth2TokenGenerator<OAuth2AccessToken> oAuth2TokenGenerator;
}
