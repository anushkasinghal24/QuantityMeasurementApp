package com.qma.auth_service.config;

import com.qma.auth_service.entity.User;
import com.qma.auth_service.repository.UserRepository;
import com.qma.auth_service.security.JwtFilter;
import com.qma.auth_service.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@Configuration
public class SecurityConfig {

    @Autowired private JwtFilter jwtFilter;
    @Autowired private UserRepository userRepository;

    @Value("${frontend.url:http://localhost:4200}")
    private String frontendUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtService jwtService) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) -> {
                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            res.setContentType("application/json");
                            res.getWriter().write("{\"error\":\"Unauthorized\"}");
                        })
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/signup",
                                "/api/auth/register",
                                "/api/auth/health",
                                "/oauth2/**",
                                "/login/**"
                        ).permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .oauth2Login(oauth -> oauth.successHandler(successHandler(jwtService)));

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(JwtService jwtService) {
        return (request, response, authentication) -> {

            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
            String email = oauthUser.getAttribute("email");
            String name  = oauthUser.getAttribute("name");

            User user = userRepository.findByEmail(email).orElseGet(() -> {
                String base = (name != null ? name.replaceAll("\\s+", "").toLowerCase() : "user");
                String username = base;
                int attempt = 1;

                while (userRepository.findByUsername(username).isPresent()) {
                    username = base + attempt++;
                }

                User u = new User();
                u.setUsername(username);
                u.setEmail(email);
                u.setPassword("");
                u.setRole("USER");
                u.setProvider("google");
                u.setCreatedAt(LocalDateTime.now());

                return userRepository.save(u);
            });

            String token = jwtService.generateToken(user.getUsername());

            String redirectUrl = UriComponentsBuilder.fromHttpUrl(frontendUrl)
                    .path("/oauth-callback")
                    .queryParam("token", token)
                    .queryParam("username", user.getUsername())
                    .queryParam("email", email != null ? email : "")
                    .build()
                    .toUriString();

            response.sendRedirect(redirectUrl);
        };
    }
}
