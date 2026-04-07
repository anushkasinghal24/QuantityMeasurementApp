package com.qma.auth_service.dto;

public class SignupRequest {
    private String username;
    private String password;

    // constructors
    public SignupRequest() {}
    public SignupRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
