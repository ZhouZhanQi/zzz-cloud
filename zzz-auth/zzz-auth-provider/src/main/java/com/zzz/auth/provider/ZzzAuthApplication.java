package com.zzz.auth.provider;

import com.zzz.framework.starter.cache.annotation.EnableZzzCache;
import com.zzz.framework.starter.core.listener.LoggingListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author: zhouzq
 * @date: 2022/7/11-10:51
 * @desc:
 */
@EnableZzzCache
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(value = {"com.zzz.*.client.**", "com.zzz.auth"})
public class ZzzAuthApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ZzzAuthApplication.class);
        application.addListeners(new LoggingListener());
        application.run(args);
    }
}
