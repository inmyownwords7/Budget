package org.java.financial.security;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.java.financial.repository.UserRepository;
import org.java.financial.entity.UserEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ✅ Fetch JPA `User` entity from the database
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // ✅ Convert `User` entity to `UserDetails`
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Password should already be hashed
                .authorities(user.getRoles().stream()
                        .map(role -> role.getAuthority()) // ✅ Convert Role to GrantedAuthority
                        .collect(Collectors.toSet()))
                .build();
    }
}
