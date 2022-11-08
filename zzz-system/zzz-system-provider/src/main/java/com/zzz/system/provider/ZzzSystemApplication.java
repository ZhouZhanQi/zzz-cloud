package com.zzz.system.provider;

import com.zzz.framework.starter.core.listener.LoggingListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhouzhanqi
 * @date 2022/6/25 19:03
 * @desc
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ZzzSystemApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ZzzSystemApplication.class);
        application.addListeners(new LoggingListener());
        application.run(args);
    }
}
