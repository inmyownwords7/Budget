package org.java.financial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ServeController handles rendering login and registration pages.
 */
@Controller
public class ServeController {

    /**
     * Displays the registration page.
     */
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // ✅ Loads templates/register.html
    }

    /**
     * Displays the login page.
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // ✅ Loads templates/login.html
    }
}
