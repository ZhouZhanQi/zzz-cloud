package com.zzz.gateway.filter;

import com.zzz.gateway.model.constants.FilterOrderedConstants;
import com.zzz.gateway.props.ZzzGatewayProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
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


    /**
     * 请求地址匹配
     */
    private PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //网关身份
        ServerHttpRequest request = exchange.getRequest();
        String requestPath = request.getURI().getRawPath();
        if (matchIgnoreUrl(requestPath)) {
            return chain.filter(exchange);
        }

        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

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
