package com.zzz.gateway;

import com.zzz.framework.starter.core.listener.LoggingListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * @author pxx
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ZzzGatewayApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ZzzGatewayApplication.class);
        application.addListeners(new LoggingListener());
        application.run(args);
    }
}
