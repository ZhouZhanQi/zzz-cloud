package com.zzz.auth.provider.support.sms;

import com.zzz.auth.provider.support.customize.ZzzOauth2AuthenticationConvert;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

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
}
