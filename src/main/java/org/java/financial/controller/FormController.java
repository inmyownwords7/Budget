package org.java.financial.controller;

import jakarta.validation.Valid;
import org.java.financial.dto.LoginDTO;
import org.java.financial.security.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * **Form Controller**
 * ✅ Handles login and registration page requests.
 */
@Controller
public class FormController {
    private final AuthService authService;

    public FormController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new LoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@Valid @ModelAttribute("user") LoginDTO loginDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        // ✅ Call `authService.authenticate()` with username & password, NOT `UserEntity`
        boolean isAuthenticated = authService.authenticate(loginDTO.getUsername(), loginDTO.getPassword());

        if (isAuthenticated) {
            redirectAttributes.addFlashAttribute("successMessage", "✅ Login successful!");
            return "redirect:/home";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Invalid username or password.");
            return "redirect:/login";
        }
    }
}
