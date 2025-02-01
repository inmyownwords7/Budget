package org.java.financial.controller;

import org.java.financial.entity.User;
import org.java.financial.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final UserService userService;

    private static final String SUPER_PASSWORD = "superpassword";

    // ✅ Constructor Injection - Use only UserService
    public ApiController(UserService userService) {
        this.userService = userService;
    }

    // ✅ 1. Register User (API)
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestParam String username, @RequestParam String password) {
        try {
            User newUser = userService.registerUser(username, password);
            return ResponseEntity.ok(Map.of("message", "User registered successfully!", "username", newUser.getUsername(), "role", "ROLE_USER"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ 2. Promote User to Admin (API)
    @PostMapping("/promote")
    public ResponseEntity<Map<String, String>> promoteToAdmin(@RequestParam String superpassword, Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(403).body(Map.of("error", "User is not authenticated!"));
        }

        if (!superpassword.equals(SUPER_PASSWORD)) {
            return ResponseEntity.status(403).body(Map.of("error", "Invalid super password!"));
        }

        try {
            User updatedUser = userService.promoteUserToAdmin(authentication.getName());
            return ResponseEntity.ok(Map.of("message", "User promoted to ADMIN!", "username", updatedUser.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ 3. Get Current User Info (API)
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getCurrentUser(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(403).body(Map.of("error", "User is not authenticated!"));
        }

        try {
            User user = userService.getUserDetails(authentication.getName());
            return ResponseEntity.ok(Map.of("username", user.getUsername(), "roles", user.getRoles()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }
}
