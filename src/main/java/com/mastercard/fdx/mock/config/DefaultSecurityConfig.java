package com.mastercard.fdx.mock.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class DefaultSecurityConfig {

    @Bean(name = "NonMTLSFilterChain")
    SecurityFilterChain publicApiSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers("/h2/**","/h2-console/**", "/health", "/startupStatus", "/shutdown",
                        "/swagger-ui/index.html", "/v3/api-docs","/error", "/favicon.ico",
                        "/consent/**", "/upload/**","/user", "/user/**")
                .permitAll();
		http.headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()));
        return http.build();
    }

    @Bean(name = "CustomMTLSFilterChain")
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain privateApiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/fdx/v6/**")
                .authenticated().and()
                .csrf().disable()
                .x509()
                .subjectPrincipalRegex("CN=(.*?)(?:,|$)")
                .userDetailsService(userDetailsService());
        http.headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()));
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(final String username) {
                log.info("UserDetailsService username={}", username);

                if (username == null || username.length() == 0) {
                    throw new UsernameNotFoundException(username);
                }

                User user = new User(username, "", AuthorityUtils.createAuthorityList("ROLE_SSL_USER"));
                return user;
            }
        };
    }

}
