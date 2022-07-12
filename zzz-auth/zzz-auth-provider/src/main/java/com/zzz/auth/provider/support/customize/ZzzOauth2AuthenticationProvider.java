package com.zzz.auth.provider.support.customize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author: zhouzq
 * @date: 2022/7/12-11:06
 * @desc: 自定义授权
 */
@Slf4j
public abstract class ZzzOauth2AuthenticationProvider<T extends ZzzOauth2AuthenticationToken> implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
