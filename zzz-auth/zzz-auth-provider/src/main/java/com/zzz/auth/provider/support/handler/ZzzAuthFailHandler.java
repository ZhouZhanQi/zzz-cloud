package com.zzz.auth.provider.support.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: zhouzq
 * @date: 2022/7/12-11:31
 * @desc: 登录认证失败处理器
 */
@Slf4j
public class ZzzAuthFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error(">>> zzz authentication failure: {}", exception.getMessage(), exception);
    }
}
