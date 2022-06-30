package com.zzz.gateway.props;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/8/18-15:12
 * @desc: 安全配置
 * </pre>
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "zzz.gateway")
@Component
public class ZzzGatewayProperties {

    /**
     * 需要登录，不需要校验权限
     */
    private List<String> publicUrls = Lists.newArrayList();

    /**
     * 不需要登录，不校验权限
     */
    private List<String> ignoreUrls = Lists.newArrayList();
}
