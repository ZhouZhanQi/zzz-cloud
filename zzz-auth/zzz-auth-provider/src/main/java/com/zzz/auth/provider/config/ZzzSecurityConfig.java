package com.zzz.auth.provider.config;

import com.zzz.auth.provider.support.base.ZzzAuthenticationProvider;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author zhouzq
 */
@EnableWebSecurity(debug = true)
public class ZzzSecurityConfig {

    @Bean
    @Order(1)
    @SneakyThrows
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
        return http.authorizeHttpRequests(httpRequest -> {
                    httpRequest.anyRequest().authenticated();
                })
                .csrf().disable()
                .formLogin(withDefaults())
                .authenticationProvider(new ZzzAuthenticationProvider())
                .build();
    }
}
