package org.java.financial.security;

import org.java.financial.entity.Role;
import org.java.financial.entity.UserEntity;
import org.java.financial.exception.UserAlreadyExistsException;
import org.java.financial.repository.RoleRepository;
import org.java.financial.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.relation.RoleNotFoundException;

@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Register user without handling redirects
    public boolean register(String username, String password, String role) throws RoleNotFoundException {
        if (userRepository.existsByUsername(username)) {
            logger.warn("User '{}' already exists, registration failed.", username);
            throw new UserAlreadyExistsException("User already exists");
        }

        final String assignedRole = (role != null && role.startsWith("ROLE_")) ? role : "ROLE_USER";

        Role userRole = roleRepository.findByRoleName(assignedRole)
                .orElseThrow(() -> new RoleNotFoundException("Role '" + assignedRole + "' not found"));

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(userRole);

        userRepository.save(user);
        logger.info("New user '{}' registered successfully with role '{}'", user.getUsername(), user.getRole().getRoleName());

        return true; // Return success
    }

    // ✅ Validate user credentials
    public boolean validateUserCredentials(String username, String password) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserAlreadyExistsException("Invalid username or password"));

        return passwordEncoder.matches(password, user.getPassword());
    }
}
