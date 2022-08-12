package com.zzz.system.provider.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author zhouzq
 */
@EnableWebSecurity(debug = true)
public class ZzzSystemSecurityConfig {

    @Bean
    @SneakyThrows
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests(httpRequest -> httpRequest
                        .anyRequest().permitAll());
        return http.build();
    }
}
