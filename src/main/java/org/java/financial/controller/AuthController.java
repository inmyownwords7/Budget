package org.java.financial.controller;

import org.java.financial.dto.LoginDTO;
import org.java.financial.dto.RegisterDTO;
import org.java.financial.entity.UserEntity;
import org.java.financial.exception.RoleNotFoundException;
import org.java.financial.security.AuthService;
import org.java.financial.service.UserService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * **Auth Controller**
 * Handles user authentication (login) and registration.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    /**
     * ✅ Constructor for AuthController.
     */
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    /**
     * ✅ Show registration instructions.
     */
    @GetMapping("/register")
    public String showRegisterPage() {
        return "Please use POST /do-register with JSON body {username, password, role}.";
    }

    /**
     * ✅ Show login instructions.
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "Please use POST /do-login with JSON body {username, password}.";
    }

    /**
     * ✅ Registers a new user securely.
     */
    @PostMapping("/do-register")
    public UserEntity registerUser(@Valid @RequestBody RegisterDTO registerDTO) throws RoleNotFoundException {
        return userService.registerUser(registerDTO.getUsername(), registerDTO.getPassword(), registerDTO.getRole());
    }

    /**
     * ✅ Authenticates a user using `LoginDTO` object.
     */
    @PostMapping("/do-login")
    public boolean loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        return authService.authenticate(loginDTO.getUsername(), loginDTO.getPassword()); // ✅ Pass only needed fields
    }
}
