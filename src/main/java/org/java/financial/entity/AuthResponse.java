package org.java.financial.entity;

public class AuthResponse {
    private final String username;
    private final String role;

    public AuthResponse(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getRole() { return role; }
}
