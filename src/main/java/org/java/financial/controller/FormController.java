package org.java.financial.controller;

import org.java.financial.entity.Role;
import org.java.financial.entity.User;
import org.java.financial.repository.RoleRepository;

import org.java.financial.repository.UserRepository;
import org.java.financial.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
public class FormController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository; // Inject the UserRepository

    // Constructor injection of services
    public FormController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;  // Initialize the UserRepository
    }

    // Constructor injection of services
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password,
                               @RequestParam(required = false) String roleName, RedirectAttributes redirectAttributes) {

        // Assign role (default to "ROLE_USER" if none is provided)
        Role role = roleName != null && !roleName.isEmpty()
                ? roleRepository.findByRoleName(roleName)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleName(roleName);
                    return roleRepository.save(newRole);
                })
                : roleRepository.findByRoleName("ROLE_USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleName("ROLE_USER");
                    return roleRepository.save(newRole);
                });

        try {
            // Password encoding (ensure you encode passwords for security)
            String encodedPassword = passwordEncoder.encode(password);

            // Create the new user (this is the missing part!)
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(encodedPassword); // Set the encoded password
            newUser.setRoles(Set.of(role)); // Assign role to user

            // Save user to the userRepository (this was missing in the original code)
            userRepository.save(newUser);

            redirectAttributes.addFlashAttribute("success", "User registered successfully");
            return "redirect:/login"; // Redirect to login page after successful registration
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register"; // Stay on registration page if there's an error
        }
    }
}
