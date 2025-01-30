package org.java.financial.controller;

import org.java.financial.entity.Role;
import org.java.financial.entity.User;
import org.java.financial.repository.RoleRepository;
import org.java.financial.repository.UserRepository;
import org.java.financial.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;

    private static final String SUPER_PASSWORD = "superpassword";

    public ApiController(UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    // ✅ 1. Register User (API)
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestParam String username, @RequestParam String password) {
        Role role = roleRepository.findByRoleName("ROLE_USER").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setRoleName("ROLE_USER");
            return roleRepository.save(newRole);
        });

        try {
            User newUser = userService.registerUser(username, password, role);
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

        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found!"));
        }

        if (!superpassword.equals(SUPER_PASSWORD)) {
            return ResponseEntity.status(403).body(Map.of("error", "Invalid super password!"));
        }

        Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setRoleName("ROLE_ADMIN");
            return roleRepository.save(newRole);
        });

        user.setRole(adminRole);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "User promoted to ADMIN!", "username", user.getUsername()));
    }

    // ✅ 3. Get Current User Info (API)
    @GetMapping("/user")
    public ResponseEntity<Map<String, String>> getCurrentUser(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(403).body(Map.of("error", "User is not authenticated!"));
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return ResponseEntity.status(404).body(Map.of("error", "User not found!"));
        }

        return ResponseEntity.ok(Map.of("username", user.getUsername(), "role", user.getRole().getRoleName()));
    }
}
