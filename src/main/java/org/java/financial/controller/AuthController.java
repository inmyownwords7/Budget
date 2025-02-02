package org.java.financial.controller;

import jakarta.validation.Valid;
import org.java.financial.dto.UserRegistrationDTO;
import org.java.financial.exception.UserAlreadyExistsException;
import org.java.financial.security.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // ✅ Show login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Thymeleaf login.html
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new UserRegistrationDTO()); // ✅ Ensure 'user' exists in the model
        return "register"; // Thymeleaf template
    }


    @PostMapping("/do-register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDTO userDTO,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register"; // Stay on the registration page if validation fails
        }

        try {
            authenticationService.register(userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole());
            redirectAttributes.addFlashAttribute("successMessage", "✅ Registration successful! Please log in.");
            return "redirect:/login"; // ✅ Redirect to login, not register
        } catch (UserAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "⚠️ User already exists.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ An error occurred.");
        }
        return "redirect:/register";
    }

//    // ✅ Handle user login manually (optional, as Spring Security usually handles it)
//    @PostMapping("/do-login")
//    public String loginUser(@RequestParam String username,
//                            @RequestParam String password,
//                            RedirectAttributes redirectAttributes) {
//        boolean isValid = authenticationService.validateUserCredentials(username, password);
//
//        if (isValid) {
//            redirectAttributes.addFlashAttribute("successMessage", "Login successful!");
//            return "redirect:/dashboard";
//        } else {
//            redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password.");
//            return "redirect:/login?error=true";
//        }
//    }
}
