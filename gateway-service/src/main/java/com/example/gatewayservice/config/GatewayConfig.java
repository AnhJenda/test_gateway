package com.example.gatewayservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableHystrix
@EnableWebSecurity
public class GatewayConfig {

    @Autowired
    AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-list", r -> r.path("/users/get")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))

                .route("user-test", r -> r.path("/test")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))

                .route("auth-service", r -> r.path("/auth/register")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .build();
    }
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable() // Tắt CSRF protection
                .authorizeExchange()
                .pathMatchers("/auth/register**").permitAll() // Cấu hình các endpoint public
                .pathMatchers("/users/**").hasRole("manager")
                .anyExchange().authenticated(); // Yêu cầu xác thực cho các endpoint khác

        return http.build();
    }
}