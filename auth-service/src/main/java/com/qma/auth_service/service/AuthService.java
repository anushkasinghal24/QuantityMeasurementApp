package com.qma.auth_service.service;

import com.qma.auth_service.dto.LoginRequest;
import com.qma.auth_service.dto.RegisterRequest;
import com.qma.auth_service.entity.User;
import com.qma.auth_service.repository.UserRepository;
import com.qma.auth_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    // Register method (unchanged)
    public String register(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        userRepository.save(user);
        return "User Registered";
    }

    // Login method (updated to return JWT token)
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // JWT token generate kar rahe hai
        return jwtUtil.generateToken(user.getEmail());
    }
}