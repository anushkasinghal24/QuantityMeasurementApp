package com.qma.api_gateway.filter;

import com.qma.api_gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtGatewayFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        System.out.println("🔥 REQUEST PATH = " + path);

        // ✅ PUBLIC APIs
        if (path.startsWith("/auth/") || path.startsWith("/qma/public/")) {
            return chain.filter(exchange);
        }

        // 🔐 CHECK TOKEN
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        String email;

        try {
            email = jwtUtil.extractEmail(token);
            System.out.println("✅ EMAIL FROM TOKEN = " + email);
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 🔥 IMPORTANT FIX: ADD EMAIL TO REQUEST HEADER
        ServerWebExchange modifiedExchange = exchange.mutate()
                .request(r -> r.headers(headers -> headers.set("X-User-Email", email)))
                .build();

        return chain.filter(modifiedExchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}