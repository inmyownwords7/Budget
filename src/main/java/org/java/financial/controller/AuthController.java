package org.java.financial.controller;

import org.java.financial.entity.UserEntity;
import org.java.financial.security.AuthService;
import org.java.financial.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

/**
 * **Auth Controller**
 * <p>
 * Handles user authentication (login) and registration.
 * </p>
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService; // ✅ Added UserService


    /**
     * ✅ **GET Endpoint for Registration Page**
     * Allows frontend to request the registration page.
     */
    @GetMapping("/register")
    public String showRegisterPage() {
        return "Please use POST /register with username, password, and role.";
    }

    /**
     * ✅ **GET Endpoint for Login Page**
     * Allows frontend to request the login page.
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "Please use POST /login with username and password.";
    }

    /**
     * **Constructor for AuthController**
     *
     * @param authService Service for user authentication.
     * @param userService Service for user registration & management.
     */
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService; // ✅ Assign UserService
    }

    /**
     * **Registers a new user.**
     *
     * @param username The username for the new user.
     * @param password The user's password.
     * @param role The role assigned to the user (e.g., "ROLE_USER").
     * @return The created {@link UserEntity}.
     * @throws RoleNotFoundException If the role does not exist.
     */
    @PostMapping("/do-register")
    public UserEntity registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role) throws RoleNotFoundException {
        return userService.registerUser(username, password, role); // ✅ Fixed reference
    }

    /**
     * **Authenticates a user by checking credentials.**
     *
     * @param username The username.
     * @param password The user's password.
     * @return `true` if authentication succeeds, `false` otherwise.
     */
    @PostMapping("/do-login")
    public boolean loginUser(
            @RequestParam String username,
            @RequestParam String password) {
        return authService.authenticateUser(username, password); // ✅ Fixed reference
    }
}
