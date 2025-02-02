package org.java.financial.dto;

import java.io.Serializable;

/**
 * DTO for sending authentication responses after a successful login.
 */
public class AuthResponse implements Serializable {

    private final String username;
    private final String role;
    private final String token;



    public AuthResponse(String username, String role, String token) {
        this.username = username;
        this.role = role;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", token='" + (token != null ? token : "N/A") + '\'' +
                '}';
    }
}
