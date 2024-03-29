package com.zzz.auth.provider;

import com.zzz.framework.starter.core.listener.LoggingListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: zhouzq
 * @date: 2022/7/11-10:51
 * @desc:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.zzz.*.client.service"})
@ComponentScan(value = {"com.zzz.auth", "com.zzz.*.client.service"})
public class ZzzAuthApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ZzzAuthApplication.class);
        application.addListeners(new LoggingListener());
        application.run(args);
    }
}
