package org.java.financial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for user registration form input.
 */
public class UserRegistrationDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Role is required")
    private String role; // e.g., "ROLE_USER" or "ROLE_ADMIN"

    // ✅ Constructors
    public UserRegistrationDTO() {}

    public UserRegistrationDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // ✅ Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
