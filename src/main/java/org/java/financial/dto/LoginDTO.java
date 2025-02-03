package org.java.financial.dto;

import jakarta.validation.constraints.NotBlank;
import org.java.financial.entity.UserEntity;

public class LoginDTO {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    // ✅ Convert LoginDTO → UserEntity
    public UserEntity toUserEntity() {
        UserEntity user = new UserEntity();
        user.setUsername(this.username);
        user.setPassword(this.password);
        return user;
    }

    // ✅ Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
