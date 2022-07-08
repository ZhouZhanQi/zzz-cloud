package com.zzz.auth.provider.config;

import com.zzz.auth.provider.support.base.ZzzAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author zhouzq
 */
public class ZzzSecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {

        http.authorizeHttpRequests(httpRequest -> {
            httpRequest.antMatchers("/token/*").permitAll();
        });
        http.authenticationProvider(new ZzzAuthenticationProvider());
        return http.build();
    }


}
