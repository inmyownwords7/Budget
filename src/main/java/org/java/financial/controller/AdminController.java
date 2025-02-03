package org.java.financial.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.java.financial.dto.RoleChangeDTO;
import org.java.financial.entity.Role;
import org.java.financial.entity.UserEntity;
import org.java.financial.exception.UserNotFoundException;
import org.java.financial.exception.InvalidCredentialsException;
import org.java.financial.repository.RoleRepository;
import org.java.financial.repository.UserRepository;
import org.java.financial.logging.GlobalLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * **Admin Controller**
 * ✅ Manages user role promotions and demotions.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * ✅ Self-promote from USER to ADMIN (requires password verification)
     */
    @PostMapping("/self-promote")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, String>> selfPromoteToAdmin(
            @Valid @RequestBody RoleChangeDTO roleChangeDTO, HttpServletRequest request) {

        return changeUserRole(roleChangeDTO, request);
    }

    /**
     * ✅ Promote any user to ADMIN (Admin-only action)
     */
    @PostMapping("/promote")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> promoteUserToAdmin(@Valid @RequestBody RoleChangeDTO roleChangeDTO) {
        return changeUserRole(roleChangeDTO, null);
    }

    /**
     * ✅ Demote ADMIN back to USER (Admin-only action)
     */
    @PostMapping("/demote")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> demoteAdminToUser(@Valid @RequestBody RoleChangeDTO roleChangeDTO) {
        return changeUserRole(roleChangeDTO, null);
    }

    /**
     * ✅ Check a user's current role
     */
    @GetMapping("/role/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> getUserRole(@PathVariable String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("❌ User not found."));

        return ResponseEntity.ok(Map.of(
                "username", username,
                "role", user.getRole().getRoleName()  // ✅ Now this works!
        ));
    }


    /**
     * ✅ Generic method to change a user's role (requires password verification)
     */
    private ResponseEntity<Map<String, String>> changeUserRole(RoleChangeDTO roleChangeDTO, HttpServletRequest request) {
        UserEntity user = userRepository.findByUsername(roleChangeDTO.getUsername())
                .orElseThrow(() -> new UserNotFoundException("❌ User not found."));

        // ✅ Verify password before allowing role change
        if (!passwordEncoder.matches(roleChangeDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("❌ Invalid password for user: " + roleChangeDTO.getUsername());
        }

        // ✅ Prevent unnecessary role updates
        if (user.getRole().getRoleName().equalsIgnoreCase(roleChangeDTO.getNewRole())) {
            return ResponseEntity.badRequest().body(Map.of("error", "⚠️ User is already " + roleChangeDTO.getNewRole()));
        }

        // ✅ Ensure role exists
        Role role = roleRepository.findByRoleName(roleChangeDTO.getNewRole())
                .orElseThrow(() -> new RuntimeException("❌ Role " + roleChangeDTO.getNewRole() + " does not exist."));

        // ✅ Update role
        user.setRole(role);
        userRepository.save(user);

        GlobalLogger.LOGGER.info("🔹 User {} role changed to {}", roleChangeDTO.getUsername(), roleChangeDTO.getNewRole());

        // ✅ If user is self-promoting, require re-login
        if (request != null) {
            request.getSession().invalidate();
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok(Map.of("message", "✅ Your role has been updated to " + roleChangeDTO.getNewRole() + ". Please log in again."));
        }

        return ResponseEntity.ok(Map.of("message", "✅ User " + roleChangeDTO.getUsername() + " has been updated to " + roleChangeDTO.getNewRole()));
    }
}
