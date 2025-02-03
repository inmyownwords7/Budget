package org.java.financial.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * ✅ DTO for handling user role updates.
 */
public class RoleChangeDTO {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "New role is required")
    private String newRole;

    // ✅ Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNewRole() { return newRole; }
    public void setNewRole(String newRole) { this.newRole = newRole; }
}
