package org.java.financial.controller;

import org.java.financial.dto.UserResponseDTO;
import org.java.financial.entity.AuthRequest;
import org.java.financial.entity.AuthResponse;
import org.java.financial.entity.UserEntity;
import org.java.financial.security.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * **AuthController** handles authentication operations such as login and registration.
 * <p>
 * This controller is responsible for handling user authentication via API.
 * </p>
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    /**
     * Constructor for **AuthController**.
     *
     * @param authenticationService The authentication service used for handling login and registration.
     */
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Handles **user login** via API.
     *
     * @param request The authentication request containing username and password.
     * @return A response containing the authenticated user's details or an error message.
     */
    @PostMapping("/do-login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            AuthResponse response = authenticationService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Handles **user registration** via API.
     *
     * @param user The user details (username, password, role).
     * @return A response containing the registered user's details or an error message.
     */
    @PostMapping("/do-register")
    public ResponseEntity<?> register(@RequestBody UserEntity user) {
        try {
            UserEntity newUser = authenticationService.register(
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole().getRoleName()
            );

            return ResponseEntity.ok(new UserResponseDTO(newUser.getUsername(), newUser.getRole().getRoleName()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (javax.management.relation.RoleNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
