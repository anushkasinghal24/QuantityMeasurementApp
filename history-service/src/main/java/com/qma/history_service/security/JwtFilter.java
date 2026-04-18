package com.qma.history_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Value("${JWT_SECRET:myveryveryverylongsecretkey123456789}")
    private String SECRET;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String xUsername = request.getHeader("X-User-Name");
        if (xUsername != null && !xUsername.isEmpty()) {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(xUsername, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                if (!claims.getExpiration().before(new Date())) {
                    String username = claims.getSubject();
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                System.out.println("JWT error in history service: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}