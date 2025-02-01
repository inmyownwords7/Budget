package org.java.financial.security;

import org.java.financial.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.List;

/**
 * SecurityConfiguration class sets up authentication and authorization rules for the application.
 * It defines access control for different endpoints and configures authentication mechanisms.
 */
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, PersistentTokenRepository tokenRepository) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for testing; enable later if needed
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/do-register", "/login", "/public/**").permitAll() // ✅ Allow registration/login
                        .requestMatchers("/dashboard", "/form").hasAuthority("ROLE_USER")
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // Admin protection
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login") // ✅ Ensure this page exists
                        .loginProcessingUrl("/do-login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll().successHandler((request, response, authentication) -> {
                            request.getSession().setMaxInactiveInterval(3600); // 1-hour session timeout
                            response.sendRedirect("/dashboard");
                        })
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("uniqueAndSecretKey") // Change this key
                        .tokenRepository(tokenRepository((DataSource) tokenRepository))
                        .tokenValiditySeconds(604800) // 7 days
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(List.of(authProvider));
    }

@Bean
public PersistentTokenRepository tokenRepository(DataSource dataSource) {
    JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
    jdbcTokenRepository.setDataSource(dataSource);

    // ✅ This will create the `persistent_logins` table if it doesn't exist
    jdbcTokenRepository.setCreateTableOnStartup(true);
    return jdbcTokenRepository;
}
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
