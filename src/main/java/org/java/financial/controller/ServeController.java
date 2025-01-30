package org.java.financial.controller;

import org.java.financial.entity.Role;
import org.java.financial.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ServeController {

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // Loads templates/register.html
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Loads templates/login.html
    }

    @GetMapping("/dashboard")
    public String showDashboardPage(Model model, @AuthenticationPrincipal User user) {
        if (user != null) {
            model.addAttribute("username", user.getUsername());

            // Get the roles as a collection and convert it to a string (or list of role names)
            Set<String> roleNames = user.getRoles().stream()
                    .map(Role::getRoleName)  // Extract role names
                    .collect(Collectors.toSet());  // Collect role names as a Set

            model.addAttribute("roles", roleNames);  // Add roles to the model
        } else {
            model.addAttribute("username", "Guest");
            model.addAttribute("roles", "None");
        }

        return "dashboard"; // This will load the dashboard.html page
    }
}
