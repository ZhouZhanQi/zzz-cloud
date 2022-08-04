package com.zzz.auth.provider.support.sms;

import com.zzz.auth.provider.support.customize.ZzzOauth2AuthenticationConvert;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * @author: zhouzq
 * @date: 2022/7/12-11:13
 * @desc:
 */
public class OAuth2ResourceOwnerSmsAuthenticationConvert extends ZzzOauth2AuthenticationConvert<OAuth2ResourceOwnerSmsAuthenticationToken> {

    @Override
    public Authentication convert(HttpServletRequest request) {
        return null;
    }

    @Override
    public boolean support(String grantType) {
        return false;
    }

    @Override
    public void checkRequestParam(HttpServletRequest request) {

    }

    @Override
    public OAuth2ResourceOwnerSmsAuthenticationToken buildToken(Authentication clientPrincipal, Set<String> requestedScopes, Map<String, Object> additionalParameters) {
        return null;
    }
}
