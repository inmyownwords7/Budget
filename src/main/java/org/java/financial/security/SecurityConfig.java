package org.java.financial.security;

import org.java.financial.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;  // Inject CustomUserDetailsService
    private final PasswordEncoder passwordEncoder;

    // Constructor injection of CustomUserDetailsService and PasswordEncoder
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
this.passwordEncoder = passwordEncoder;
    }
   @Bean
   public CustomUserDetailsService customUserDetailsService(UserRepository userRepository) {
       return new CustomUserDetailsService(userRepository);
   }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for H2 Console
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**", "/register", "/login", "/css/**").permitAll() // Whitelist endpoints
                        .requestMatchers("/dashboard").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login") // ✅ Custom login page (if using Thymeleaf)
                        .defaultSuccessUrl("/dashboard", true) // ✅ Redirect to dashboard after login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // ✅ Redirect to login after logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();  // Handle user authentication
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        // This is where you're telling Spring that you want to use BCrypt to encode passwords.
        return new BCryptPasswordEncoder(); // A specific implementation of PasswordEncoder.
    }
}
