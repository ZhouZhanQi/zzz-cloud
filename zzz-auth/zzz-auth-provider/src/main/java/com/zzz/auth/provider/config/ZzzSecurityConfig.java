package com.zzz.auth.provider.config;

import com.zzz.auth.provider.support.base.ZzzAuthenticationProvider;
import com.zzz.framework.common.exceptions.FrameworkException;
import lombok.SneakyThrows;
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
    @SneakyThrows
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
        return http.authorizeHttpRequests(httpRequest -> {
                    try {
                        httpRequest.antMatchers("/oauth2/token", "/oauth2/authorize").permitAll().and().csrf().disable();
                    } catch (Exception e) {
                        throw new FrameworkException(e);
                    }
                })
                .authenticationProvider(new ZzzAuthenticationProvider())
                .build();
    }
}
