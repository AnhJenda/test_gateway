package com.example.gatewayservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.servlet.*;
import java.io.IOException;

@Component
public class AuthenticationFilter implements GatewayFilter, WebFilter {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (validator.isSecured.test(exchange.getRequest())) {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("missing authorization header");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);

                try {
                    // Kiểm tra token
                    jwtUtil.validateJwtToken(authHeader);

                    // Trích xuất vai trò từ token
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(jwtUtil.getSignKey())
                            .build()
                            .parseClaimsJws(authHeader)
                            .getBody();

                    String userRole = (String) claims.get("role");

                    // So sánh vai trò với quyền truy cập
                    if ("manager".equals(userRole)) {
                        // Người dùng có quyền truy cập vào endpoint admin
                        return chain.filter(exchange);
                    } else {
                        // Người dùng không có quyền truy cập
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return exchange.getResponse().setComplete();
                    }

                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("unauthorized access to application");
                }
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (validator.isSecured.test(exchange.getRequest())) {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("missing authorization header");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);

                try {
                    // Kiểm tra token
                    jwtUtil.validateJwtToken(authHeader);

                    // Trích xuất vai trò từ token
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(jwtUtil.getSignKey())
                            .build()
                            .parseClaimsJws(authHeader)
                            .getBody();

                    String userRole = (String) claims.get("role");

                    // So sánh vai trò với quyền truy cập
                    if ("manager".equals(userRole)) {
                        // Người dùng có quyền truy cập vào endpoint admin
                        return chain.filter(exchange);
                    } else {
                        // Người dùng không có quyền truy cập
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return exchange.getResponse().setComplete();
                    }

                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("unauthorized access to application");
                }
            }
        }
        return chain.filter(exchange);
    }
}

