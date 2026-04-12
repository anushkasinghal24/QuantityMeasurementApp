package com.qma.auth_service.service;

import com.qma.auth_service.dto.LoginRequest;
import com.qma.auth_service.entity.User;
import com.qma.auth_service.repository.UserRepository;
import com.qma.auth_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    public User signup(User user) {
        return repo.save(user);
    }

    public String login(LoginRequest request) {

        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}