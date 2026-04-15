//package com.qma.api_gateway.filter;
//
//import com.qma.api_gateway.util.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@Component
//public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    private static final List<String> PUBLIC_PATHS = List.of(
//            "/api/auth/login",
//            "/api/auth/signup",
//            "/api/auth/register",
//            "/api/quantity/convert",
//            "/api/auth/health",
//            "/oauth2/",
//            "/login/oauth2/",
//            "/actuator/",
//            "/v3/api-docs",
//            "/swagger-ui",
//            "/swagger"
//    );
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        var request = exchange.getRequest();
//        var response = exchange.getResponse();
//
//        String path = request.getURI().getPath();
//        HttpMethod method = request.getMethod();
//
//        // =========================
//        // 1. HANDLE CORS PREFLIGHT
//        // =========================
//        if (method == HttpMethod.OPTIONS) {
//            response.setStatusCode(HttpStatus.OK);
//            return response.setComplete();
//        }
//
//        // =========================
//        // 2. PUBLIC PATH CHECK
//        // =========================
//        boolean isPublic = PUBLIC_PATHS.stream().anyMatch(path::startsWith);
//
//        if (isPublic) {
//            return chain.filter(exchange);
//        }
//
//        // =========================
//        // 3. AUTH HEADER CHECK
//        // =========================
//        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();
//        }
//
//        String token = authHeader.substring(7);
//
//        try {
//            if (!jwtUtil.validateToken(token)) {
//                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                return response.setComplete();
//            }
//
//            String username = jwtUtil.extractUsername(token);
//
//            ServerWebExchange mutated = exchange.mutate()
//                    .request(r -> r.header("X-User-Name", username))
//                    .build();
//
//            return chain.filter(mutated);
//
//        } catch (Exception e) {
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();
//        }
//    }
//
//    @Override
//    public int getOrder() {
//        return -1;
//    }
//}

package com.qma.api_gateway.filter;

import com.qma.api_gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/auth/login",
            "/api/auth/signup",
            "/api/auth/register",
            "/api/auth/health",
            "/api/quantity/test",
            "/api/quantity/health",
            "/api/quantity/convert",
            "/api/quantity/arithmetic/",
            "/api/quantity/add",
            "/api/quantity/subtract",
            "/api/quantity/compare",
            "/api/quantity/divide",
            "/oauth2/",
            "/login/oauth2/",
            "/actuator/",
            "/v3/api-docs",
            "/swagger-ui",
            "/swagger"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        var request = exchange.getRequest();
        var response = exchange.getResponse();

        String path = request.getURI().getPath();
        HttpMethod method = request.getMethod();

        // =========================
        // 1. HANDLE CORS PREFLIGHT
        // =========================
        if (method == HttpMethod.OPTIONS) {
            response.setStatusCode(HttpStatus.OK);
            return response.setComplete();
        }

        // =========================
        // 2. PUBLIC PATH CHECK
        // =========================
        boolean isPublic = PUBLIC_PATHS.stream().anyMatch(path::startsWith);

        if (isPublic) {
            return chain.filter(exchange);
        }

        // =========================
        // 3. AUTH HEADER CHECK
        // =========================
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        String token = authHeader.substring(7);

        try {
            if (!jwtUtil.validateToken(token)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            String username = jwtUtil.extractUsername(token);

            ServerWebExchange mutated = exchange.mutate()
                    .request(r -> r.header("X-User-Name", username))
                    .build();

            return chain.filter(mutated);

        } catch (Exception e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
