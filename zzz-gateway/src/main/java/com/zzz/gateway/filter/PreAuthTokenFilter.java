package com.zzz.gateway.filter;

import com.zzz.framework.common.exceptions.BusinessException;
import com.zzz.framework.common.util.AssertUtils;
import com.zzz.framework.starter.security.model.code.SecurityExceptionCode;
import com.zzz.gateway.model.constants.FilterOrderedConstants;
import com.zzz.gateway.props.ZzzGatewayProperties;
import com.zzz.gateway.util.AccessTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/10/21-10:47
 * @desc: token验证过滤器
 * </pre>
 */
@RequiredArgsConstructor
@Component
public class PreAuthTokenFilter implements GlobalFilter, Ordered {

    private final ZzzGatewayProperties securityProperties;

    private final OAuth2AuthorizationService oAuth2AuthorizationService;

    /**
     * 请求地址匹配
     */
    private final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //网关身份
        ServerHttpRequest request = exchange.getRequest();
        String requestPath = request.getURI().getRawPath();
        if (matchIgnoreUrl(requestPath)) {
            return chain.filter(exchange);
        }


        String headAuthorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        AssertUtils.checkArgument(StringUtils.hasLength(headAuthorization), SecurityExceptionCode.TOKEN_INFO_IS_EMPTY.getMessage());

        OAuth2Authorization oAuth2Authorization = oAuth2AuthorizationService.findByToken(AccessTokenUtils.getTokenFromHead(headAuthorization), OAuth2TokenType.ACCESS_TOKEN);
        //校验token正确性
        AssertUtils.checkNotNull(oAuth2Authorization, SecurityExceptionCode.TOKEN_INFO_IS_EMPTY.getMessage());

        //校验token
        if (matchPublicUrl(requestPath)) {
            return chain.filter(exchange);
        }
        //校验地址是否需要权限
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return FilterOrderedConstants.PRE_AUTH_TOKE;
    }

    /**
     * 查询是否匹配公共请求地址
     * 需要token 不需要鉴权
     * @param requestPath
     * @return
     */
    private boolean matchPublicUrl(String requestPath) {
        return securityProperties.getPublicUrls().stream().anyMatch(publicUrl -> pathMatcher.match(publicUrl, requestPath));
    }

    /**
     * 匹配忽略权限接口
     * @param requestPath
     * @return
     */
    private boolean matchIgnoreUrl(String requestPath) {
        return securityProperties.getIgnoreUrls().stream().anyMatch(ignoreUrl -> pathMatcher.match(ignoreUrl, requestPath));
    }
}
