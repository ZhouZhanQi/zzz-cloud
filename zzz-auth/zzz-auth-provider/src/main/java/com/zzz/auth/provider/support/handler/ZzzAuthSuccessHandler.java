package com.zzz.auth.provider.support.handler;

import cn.hutool.http.ContentType;
import com.zzz.framework.common.model.code.CommonExceptionCode;
import com.zzz.framework.starter.core.model.ResponseData;
import com.zzz.framework.starter.web.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * @author: zhouzq
 * @date: 2022/7/12-11:30
 * @desc: 登录认证成功处理器
 */
@Slf4j
public class ZzzAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info(">>> zzz auth principal: {} authentication success", authentication.getPrincipal());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //推送登录成功事件 保存登录成功日志

        // 输出token
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;

        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
        Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();

        OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
                .tokenType(accessToken.getTokenType()).scopes(accessToken.getScopes());
        if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
            builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
        }
        if (refreshToken != null) {
            builder.refreshToken(refreshToken.getTokenValue());
        }
        if (!CollectionUtils.isEmpty(additionalParameters)) {
            builder.additionalParameters(additionalParameters);
        }
        // 无状态 注意删除 context 上下文的信息
        SecurityContextHolder.clearContext();
        ResponseUtils.responseWriter(response, ContentType.JSON.getValue(), CommonExceptionCode.SUCCESS.getCode(), ResponseData.success(builder.build()));
    }
}
