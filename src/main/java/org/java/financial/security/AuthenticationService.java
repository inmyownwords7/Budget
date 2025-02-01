package org.java.financial.security;

import org.java.financial.entity.AuthRequest;
import org.java.financial.entity.AuthResponse;
import org.java.financial.entity.Role;
import org.java.financial.entity.UserEntity;
import org.java.financial.exception.UserAlreadyExistsException;
import org.java.financial.repository.UserRepository;
import org.java.financial.repository.RoleRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.relation.RoleNotFoundException;

/**
 * AuthenticationService handles user authentication (login) and user registration (sign-up).
 * It interacts with the database to authenticate users and manage new user registrations securely.
 */
@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs an AuthenticationService with necessary dependencies.
     *
     * @param authenticationManager Manages authentication processes.
     * @param userRepository        Repository for user data.
     * @param roleRepository        Repository for role data.
     * @param passwordEncoder       Encoder for hashing passwords.
     */
    public AuthenticationService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticates a user based on their username and password.
     * Uses Spring Security's AuthenticationManager to verify credentials.
     *
     * @param request The authentication request containing the username and password.
     * @return An AuthResponse containing the authenticated user's username and role.
     * @throws BadCredentialsException if authentication fails due to incorrect credentials.
     */
    public AuthResponse authenticate(AuthRequest request) {
        logger.info("Attempting authentication for user: {}", request.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal(); // Correctly binding UserDetailsImpl

            logger.info("User '{}' authenticated successfully with role '{}'", userDetails.getUsername(), userDetails.getRoleName());

            return new AuthResponse(userDetails.getUsername(), userDetails.getRoleName());
        } catch (BadCredentialsException e) {
            logger.warn("Failed login attempt for user '{}'", request.getUsername());
            throw new BadCredentialsException("Invalid username or password");
        }
    }


    /**
     * Registers a new user with a securely hashed password and assigns a role.
     * Ensures the username is unique before proceeding.
     *
     * @param username The new user's username.
     * @param password The new user's raw password (will be hashed before storage).
     * @param role     The role assigned to the new user.
     * @return The saved UserEntity object representing the new user.
     * @throws UserAlreadyExistsException if the username is already taken.
     * @throws RuntimeException           if the provided role is not found in the database.
     */
    public UserEntity register(String username, String password, String role) throws RoleNotFoundException {
        if (userRepository.existsByUsername(username)) {
            logger.warn("User '{}' already exists, registration failed.", username);
            throw new UserAlreadyExistsException("User already exists");
        }

        //final String assignedRole = (role != null) ? role : "ROLE_USER"; // ✅ Declare a final variable
        final String assignedRole = (role != null && role.startsWith("ROLE_")) ? role : "ROLE_USER";

        Role userRole = roleRepository.findByRoleName(assignedRole)
                .orElseThrow(() -> {
                    logger.error("Role '{}' not found, registration failed.", assignedRole);
                    return new RoleNotFoundException("Role '" + assignedRole + "' not found");
                });

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(userRole);

        UserEntity savedUser = userRepository.save(user);
        logger.info("New user '{}' registered successfully with role '{}'", savedUser.getUsername(), savedUser.getRole().getRoleName());

        return savedUser;
    }
}
