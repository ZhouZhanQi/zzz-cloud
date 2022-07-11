package com.zzz.auth.provider.config;

import com.zzz.auth.provider.support.base.ZzzAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author zhouzq
 */
@EnableWebSecurity(debug = true)
public class ZzzSecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http.authorizeHttpRequests(httpRequest -> {
            httpRequest.antMatchers("/token/*").permitAll();
        });
        http.authenticationProvider(new ZzzAuthenticationProvider());
        return http.build();
    }
}
