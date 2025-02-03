package org.java.financial.security;

import org.java.financial.entity.UserEntity;
import org.java.financial.exception.UserNotFoundException;
import org.java.financial.exception.InvalidCredentialsException;
import org.java.financial.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * ✅ Authenticate a user using `username` and `password` instead of `UserEntity`.
     */
    public boolean authenticate(String username, String password) {
        // ✅ Fetch user from database
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("❌ User not found: " + username));

        // ✅ Compare raw password with hashed password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("❌ Invalid password for user: " + username);
        }

        return true; // ✅ Authentication successful
    }
}
