package com.example.gatewayservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebSecurity
public class GatewayConfig {

    private final AuthenticationFilter filter;

    @Autowired
    public GatewayConfig(AuthenticationFilter filter) {
        this.filter = filter;
    }

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
                .route("auth-login", r -> r.path("/auth/login")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .build();
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/auth/register**", "/auth/login**").permitAll()
                .anyExchange().authenticated()
                .and()
                .build();
    }

}
