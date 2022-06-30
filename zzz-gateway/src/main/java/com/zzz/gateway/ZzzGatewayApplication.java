package com.zzz.gateway;

import com.zzz.framework.starter.cache.annotation.EnableZzzCache;
import com.zzz.framework.starter.core.listener.LoggingListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableZzzCache
@SpringBootApplication
@EnableDiscoveryClient
public class ZzzGatewayApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ZzzGatewayApplication.class);
        application.addListeners(new LoggingListener());
        application.run(args);
    }
}
