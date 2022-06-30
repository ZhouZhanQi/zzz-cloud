package com.zzz.gateway.filter;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.zzz.gateway.model.constants.FilterOrderedConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/10/21-11:02
 * @desc: 请求日志过滤器
 * </pre>
 */
@Slf4j
@Component
public class RequestLogFilter implements GlobalFilter, Ordered {

    private static final String REQ_START_TIME = "startTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        List<Object> beforeLogArgs = Lists.newArrayList();
        String requestPath = request.getURI().getRawPath();
        String requestMethod = request.getMethod().name();
        beforeLogArgs.add(requestPath);
        beforeLogArgs.add(requestMethod);
        StringBuilder requestBeforeLog = new StringBuilder();
        requestBeforeLog.append("\n\n================>zzz gateway Request Start<================\n");
        requestBeforeLog.append("========>{} {}\n");
        requestBeforeLog.append("========>headers<========\n");
        request.getHeaders().forEach((name, value) -> {
            requestBeforeLog.append("{}:{}\n");
            beforeLogArgs.add(name);
            beforeLogArgs.add(Joiner.on(",").join(value));
        });
        requestBeforeLog.append("\n================>zzz gateway Request End<================\n");
        log.info(requestBeforeLog.toString(), beforeLogArgs.toArray());
        exchange.getAttributes().put(REQ_START_TIME, LocalDateTime.now());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            LocalDateTime startTime = exchange.getAttribute(REQ_START_TIME);
            //计算耗时
            long durationTime = LocalDateTimeUtil.between(startTime, LocalDateTime.now(), ChronoUnit.MILLIS);
            StringBuilder responseLog = new StringBuilder();
            List<Object> responseLogArgs = Lists.newArrayList();
            responseLog.append("\n\n================>zzz gateway Response Start<================\n");
            responseLog.append("========> {} {}, {} {}");
            responseLogArgs.add(requestPath);
            responseLogArgs.add(requestMethod);
            responseLogArgs.add(response.getStatusCode().value());
            responseLogArgs.add(durationTime + "ms");
            responseLog.append("========>headers<========\n");
            response.getHeaders().forEach((name, value) -> {
                responseLog.append("{}:{}\n");
                responseLogArgs.add(name);
                responseLogArgs.add(Joiner.on(",").join(value));
            });
            responseLog.append("\n================>zzz gateway Response End<================\n");
            log.info(responseLog.toString(), responseLogArgs.toArray());
        }));
    }

    @Override
    public int getOrder() {
        return FilterOrderedConstants.REQUEST_LOG_ORDERED;
    }
}
