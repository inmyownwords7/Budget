package org.java.financial.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.java.financial.entity.Role;
import org.java.financial.entity.UserEntity;
import org.java.financial.exception.UserNotFoundException;
import org.java.financial.repository.RoleRepository;
import org.java.financial.repository.UserRepository;
import org.java.financial.logging.GlobalLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for managing user role changes.
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

    /**
     * ✅ Self-promote from USER to ADMIN
     */
    @PostMapping("/self-promote")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, String>> selfPromoteToAdmin(Authentication auth, HttpServletRequest request) {
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        return changeUserRole(username, "ROLE_ADMIN", request);
    }

    /**
     * ✅ Promote any user to ADMIN (Admin-only action)
     */
    @PostMapping("/promote/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> promoteUserToAdmin(@PathVariable String username) {
        return changeUserRole(username, "ROLE_ADMIN", null);
    }

    /**
     * ✅ Demote ADMIN back to USER (Admin-only action)
     */
    @PostMapping("/demote/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> demoteAdminToUser(@PathVariable String username) {
        return changeUserRole(username, "ROLE_USER", null);
    }

    /**
     * ✅ Check a user's current role
     */
    @GetMapping("/role/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> getUserRole(@PathVariable String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        return ResponseEntity.ok(Map.of("username", username, "role", user.getRole().getRoleName()));
    }

    /**
     * ✅ Generic method to change a user's role
     */
    private ResponseEntity<Map<String, String>> changeUserRole(String username, String newRole, HttpServletRequest request) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        if (user.getRole().getRoleName().equalsIgnoreCase(newRole)) {
            return ResponseEntity.badRequest().body(Map.of("error", "User is already " + newRole));
        }

        Role role = roleRepository.findByRoleName(newRole)
                .orElseThrow(() -> new RuntimeException("Role " + newRole + " does not exist."));

        user.setRole(role);
        userRepository.save(user);

        GlobalLogger.LOGGER.info("User {} role changed to {}", username, newRole);

        // If user is self-promoting, invalidate session & require re-login
        if (request != null) {
            request.getSession().invalidate();
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok(Map.of("message", "Your role has been updated to " + newRole + ". Please log in again."));
        }

        return ResponseEntity.ok(Map.of("message", "User " + username + " has been updated to " + newRole));
    }
}
