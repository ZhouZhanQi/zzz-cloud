package com.zzz.oauth.provider;

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
public class ZzzOauthApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ZzzOauthApplication.class);
        application.addListeners(new LoggingListener());
        application.run(args);
    }
}
