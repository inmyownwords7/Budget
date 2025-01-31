package org.java.financial.controller;

import org.java.financial.entity.AuthRequest;
import org.java.financial.entity.AuthResponse;
import org.java.financial.entity.User;
import org.java.financial.security.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController handles authentication (login & registration) requests.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * ✅ User Login Endpoint
     * @param request - contains username & password.
     * @return AuthResponse - contains username & role if authentication is successful.
     */
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
//        return ResponseEntity.ok(authenticationService.authenticate(request));
//    }

    /**
     * ✅ User Registration Endpoint
     * @param user - contains username, password, and role.
     * @return ResponseEntity - created user if successful.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User newUser = authenticationService.register(
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole().getRoleName()
            );
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
