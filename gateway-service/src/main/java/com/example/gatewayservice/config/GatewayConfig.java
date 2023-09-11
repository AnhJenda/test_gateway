package com.example.gatewayservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.io.IOException;


@Configuration
@EnableWebFlux
public class GatewayConfig extends GatewayAutoConfiguration{

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                // Các endpoint không cần xác thực
                .pathMatchers("/auth/**").permitAll()
                // Các endpoint cần xác thực và quyền truy cập
                .pathMatchers("/users/getall").hasAuthority("manager")
                .anyExchange().authenticated()
                .and()
                .build();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Các route không cần xác thực
                .route("auth-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://user-service"))
                // Các route cần xác thực
                .route("user-service", r -> r.path("/users/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://user-service"))
                .build();
    }
}
