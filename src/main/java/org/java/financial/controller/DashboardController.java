package org.java.financial.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling dashboard requests.
 */
@Controller
public class DashboardController {

    /**
     * Displays the dashboard page.
     *
     * @param model The model to hold user authentication details.
     * @return The Thymeleaf template name for the dashboard.
     */
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", authentication.getName()); // ✅ Pass username to Thymeleaf
        model.addAttribute("roles", authentication.getAuthorities()); // ✅ Pass user roles
        return "dashboard"; // ✅ Loads dashboard.html
    }
}
