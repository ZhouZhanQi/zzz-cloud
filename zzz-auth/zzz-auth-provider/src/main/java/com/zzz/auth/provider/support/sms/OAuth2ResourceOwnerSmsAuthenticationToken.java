package com.zzz.auth.provider.support.sms;

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
public class OAuth2ResourceOwnerSmsAuthenticationToken extends ZzzOauth2AuthenticationToken {

    public OAuth2ResourceOwnerSmsAuthenticationToken(AuthorizationGrantType authorizationGrantType, Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(authorizationGrantType, clientPrincipal, scopes, additionalParameters);
    }
}
