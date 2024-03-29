package com.zzz.gateway.filter;


import com.zzz.framework.common.exceptions.FrameworkException;
import com.zzz.framework.common.util.AssertUtils;
import com.zzz.framework.common.util.JacksonUtils;
import com.zzz.framework.starter.cache.RedisCacheHelper;
import com.zzz.framework.starter.core.context.ZzzThreadContext;
import com.zzz.framework.starter.core.model.ZzzContext;
import com.zzz.framework.starter.core.model.ZzzUser;
import com.zzz.framework.starter.core.model.constants.CoreConstants;
import com.zzz.framework.starter.core.model.enums.CommonKeyPrefix;
import com.zzz.gateway.model.code.BasePlatformGatewayExceptionCode;
import com.zzz.gateway.model.constants.FilterOrderedConstants;
import com.zzz.gateway.util.AccessTokenUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/10/21-10:51
 * @desc: 权限认证后
 * </pre>
 */
@RequiredArgsConstructor
@Component
public class AfterAuthFilter implements GlobalFilter, Ordered {

    private final RedisCacheHelper<ZzzUser> redisCacheHelper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //网关身份
        ServerHttpRequest request = exchange.getRequest();
        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(authorization) || !authorization.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = AccessTokenUtils.getTokenFromHead(authorization);
        //获取缓存用户信息 并放入线程上下文
        //清除缓存信息
        try {
            ZzzUser loginUser = redisCacheHelper.get(CommonKeyPrefix.ZZZ_USER_INFO, token);
            AssertUtils.checkNotNull(loginUser, new FrameworkException(BasePlatformGatewayExceptionCode.TOKEN_USER_INFO_EXPIRED));
            //设置用户信息
            request.mutate()
                    .headers(header -> header.add(CoreConstants.ZZZ_USER_INFO, Base64.getEncoder().encodeToString(JacksonUtils.pojo2Json(loginUser).getBytes(StandardCharsets.UTF_8))))
                    .build();
        } finally {
            redisCacheHelper.delete(CommonKeyPrefix.ZZZ_USER_INFO, token);
        }
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return FilterOrderedConstants.AFTER_AUTH_TOKEN;
    }
}
