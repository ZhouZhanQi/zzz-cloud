package com.zzz.auth.provider.support.pwd;

import com.zzz.auth.provider.support.customize.ZzzOauth2AuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
import java.util.Set;

/**
 * @author: zhouzq
 * @date: 2022/7/12-10:52
 * @desc:
 */
public class OAuth2ResourceOwnerPwdAuthenticationToken extends ZzzOauth2AuthenticationToken {

    public OAuth2ResourceOwnerPwdAuthenticationToken(AuthorizationGrantType authorizationGrantType, Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(authorizationGrantType, clientPrincipal, scopes, additionalParameters);
    }
}
