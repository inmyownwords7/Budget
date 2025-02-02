package org.java.financial.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.java.financial.entity.Role;
import org.java.financial.entity.UserEntity;
import org.java.financial.repository.RoleRepository;
import org.java.financial.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * Controller for managing role promotions.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AdminController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping(value = "/self-promote", produces = "application/json") // ✅ Ensure JSON response
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, String>> selfPromoteToAdmin(Authentication auth, HttpServletRequest request) {
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(403).body(Map.of("error", "User not found."));
        }

        UserEntity user = userOptional.get();

        if ("ROLE_ADMIN".equals(user.getRole().getRoleName())) {
            return ResponseEntity.badRequest().body(Map.of("error", "You are already an admin."));
        }

        Optional<Role> adminRole = roleRepository.findByRoleName("ROLE_ADMIN");

        if (adminRole.isEmpty()) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Admin role does not exist."));
        }

        user.setRole(adminRole.get());
        userRepository.save(user);

        request.getSession().invalidate();
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(Map.of("message", "You have been promoted to ROLE_ADMIN. Please log in again."));
    }
}
