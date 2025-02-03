package org.java.financial.controller;

import org.java.financial.dto.UserRegistrationDTO;
import org.java.financial.entity.UserEntity;
import org.java.financial.exception.*;
import org.java.financial.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * **User Controller** - Handles User Registration and Management
 */
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO request) {
        try {
            UserEntity user = userService.registerUser(request.getUsername(), request.getPassword(), request.getRole());
            return ResponseEntity.ok("✅ User '" + user.getUsername() + "' registered successfully!");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("⚠️ User already exists.");
        } catch (RoleNotFoundException e) {
            return ResponseEntity.badRequest().body("❌ Role not found.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("⚠️ An error occurred.");
        }
    }
}
