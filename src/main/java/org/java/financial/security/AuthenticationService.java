package org.java.financial.security;

import org.java.financial.entity.AuthRequest;
import org.java.financial.entity.AuthResponse;
import org.java.financial.entity.Role;
import org.java.financial.entity.User;
import org.java.financial.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
/**
 * AuthenticationService handles user authentication (login) and registration (signup).
 */
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ User Authentication (Login)
    /**
     * Authenticates a user using Spring Security's AuthenticationManager.
     * @param request - contains username and password.
     * @return AuthResponse - contains username and role if authentication is successful.
     */
    public AuthResponse authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AuthResponse(user.getUsername(), user.getRole().getRoleName());
    }
    /**
     * Registers a new user with a hashed password and assigns a role.
     * @param username - new user's username.
     * @param password - new user's raw password.
     * @param role - role assigned to the user.
     * @return User - the saved user entity.
     */
    // ✅ User Registration (Sign Up)
    public User register(String username, String password, String role) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(new Role(role));

        return userRepository.save(user);
    }
}
