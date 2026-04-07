package com.qma.auth_service.controller;

import com.qma.auth_service.dto.AuthResponse;
import com.qma.auth_service.dto.LoginRequest;
import com.qma.auth_service.dto.RegisterRequest;
import com.qma.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest request){
        String result = authService.register(request);
        return ResponseEntity.ok(result);
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        // Call AuthService.login -> returns JWT token
        String token = authService.login(request);

        // Wrap token in AuthResponse
        AuthResponse response = new AuthResponse(token);
        return ResponseEntity.ok(response);
    }
}