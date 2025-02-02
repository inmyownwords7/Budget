package org.java.financial.controller;

import jakarta.validation.Valid;
import org.java.financial.dto.UserRegistrationDTO;
import org.java.financial.exception.UserAlreadyExistsException;
import org.java.financial.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.java.financial.implementation.UserLogic;
/**
 * **Auth Controller**
 * <p>
 * Handles login and registration page requests.
 * </p>
 */
@Controller // ✅ Use @Controller instead of @RestController for Thymeleaf views
public class FormController {

    private final UserService userService;

    public FormController(UserService userService) {
        this.userService = userService;
    }

    /**
     * ✅ Show the **login page** when users visit `/login`
     */
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login"; // ✅ This returns the `login.html` template
    }

    /**
     * ✅ Show the **register page** when users visit `/register`
     */
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        return "register"; // ✅ This returns the `register.html` template
    }
    @PostMapping
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDTO userDTO,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register"; // ✅ Stay on register page if validation fails
        }

        try {
            userService.registerUser(userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole());
            redirectAttributes.addFlashAttribute("successMessage", "✅ Registration successful!");
            return "redirect:/login"; // ✅ Redirect to login after successful registration
        } catch (UserAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "⚠️ User already exists.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ An error occurred.");
        }

        return "redirect:/register"; // ✅ Redirect back if there's an error
    }
}
