package org.java.financial.controller;

import org.java.financial.dto.AuthRequest;
import org.java.financial.dto.AuthResponse;
import org.java.financial.dto.UserRegistrationDTO;
import org.java.financial.exception.UserAlreadyExistsException;
import org.java.financial.security.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import jakarta.validation.Valid;

/**
 * **API Controller** for managing authentication.
 */
@RestController
@RequestMapping("/api")
@Validated
public class ApiController {

    private final AuthenticationService authenticationService;

    public ApiController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // ───────────────────────────────────────────────────────────────────────────
    // 🔹 1️⃣ USER AUTHENTICATION API
    // ───────────────────────────────────────────────────────────────────────────

    @PostMapping("/do-register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDTO request) {
        try {
            authenticationService.register(request.getUsername(), request.getPassword(), request.getRole());
            return ResponseEntity.ok("✅ User registered successfully!");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("⚠️ User already exists.");
        } catch (RoleNotFoundException e) {
            return ResponseEntity.badRequest().body("❌ Role not found.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("⚠️ An error occurred.");
        }
    }
}
