package org.java.financial.controller;

import org.java.financial.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
            model.addAttribute("role", user.getRole().getRoleName()); // ✅ Show role
        } else {
            model.addAttribute("username", "Guest");
            model.addAttribute("role", "None");
        }
        return "dashboard"; // ✅ Loads templates/dashboard.html
    }
}
