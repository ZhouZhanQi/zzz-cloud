package com.zzz.gateway.model.constants;

import org.springframework.core.Ordered;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/10/28-17:47
 * @desc: 过滤器顺序
 * </pre>
 */
public interface FilterOrderedConstants {

    /**
     * 请求日志
     */
    Integer REQUEST_LOG_ORDERED = Ordered.LOWEST_PRECEDENCE;

    /**
     * 请求前(调用链填充)
     */
    Integer PRE_REQUEST = Ordered.HIGHEST_PRECEDENCE;

    /**
     * 请求规则
     */
    Integer PRE_RULE = 10;

    /**
     * token校验
     */
    Integer PRE_AUTH_TOKE = 20;

    /**
     * 认证之后
     */
    Integer AFTER_AUTH_TOKEN = 30;
}
