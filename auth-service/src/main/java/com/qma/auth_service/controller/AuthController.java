package com.qma.auth_service.controller;

import com.qma.auth_service.dto.LoginRequest;
import com.qma.auth_service.entity.User;
import com.qma.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return service.signup(user);
    }
    @GetMapping("/test")
    public String test() {
        return "Protected API working!";
    }
}