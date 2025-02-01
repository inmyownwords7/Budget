package org.java.financial.controller;

import org.java.financial.dto.TransactionRequest;
import org.java.financial.entity.Category;
import org.java.financial.entity.UserEntity;
import org.java.financial.repository.CategoryRepository;
import org.java.financial.security.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

/**
 * FormController serves HTML pages for user registration, login, budgets, and transactions.
 */
@Controller
@RequestMapping("/")
public class FormController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final CategoryRepository categoryRepository;

    public FormController(AuthenticationService authenticationService, AuthenticationManager authenticationManager, CategoryRepository categoryRepository) {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
        this.categoryRepository = categoryRepository;
    }

    /**
     * ✅ Displays the login form.
     *
     * @param error Optional error message.
     * @param model The model for Thymeleaf.
     * @return The login form view.
     */
    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }
        return "login";
    }
    // ✅ Load the category form in Thymeleaf
//    @GetMapping("/form")
//    public String showCategoryForm(Model model) {
//        model.addAttribute("category", new Category()); // ✅ Pass a blank Category object
//        model.addAttribute("categories", categoryRepository.findAll());
//        return "category-form"; // ✅ Thymeleaf template
//    }
    /**
     * ✅ Handles login form submission.
     */
    @PostMapping("/do-login")
    public String processLoginForm(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/dashboard"; // ✅ Redirect on successful login

        } catch (Exception e) {
            model.addAttribute("error", "Invalid credentials. Please try again.");
            return "login"; // ✅ Reload login page with error
        }
    }

    /**
     * ✅ Displays the user registration form.
     *
     * @param model The model for Thymeleaf.
     * @return The registration form view.
     */
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    /**
     * ✅ Handles the registration form submission.
     *
     * @param user  The submitted user details.
     * @param model The model for Thymeleaf.
     * @return Redirect to login page on success, or show an error on failure.
     */
    @PostMapping("/do-register")
    public String processRegisterForm(
            @ModelAttribute UserEntity user,
            Model model) {

        try {
            // ✅ Default role if not provided
            String role = (user.getRole() != null && user.getRole().getRoleName() != null)
                    ? user.getRole().getRoleName()
                    : "ROLE_USER"; // Default role

            authenticationService.register(user.getUsername(), user.getPassword(), role);

            // ✅ Automatically authenticate user after successful registration
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/dashboard"; // ✅ Redirect to dashboard

        } catch (RuntimeException | RoleNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    /**
     * ✅ Displays the category form page.
     */
//    @GetMapping("/category")
//    public String showCategoryForm(Model model) {
//        model.addAttribute("category", new Category()); // ✅ Ensure a blank Category object is passed
//        model.addAttribute("categories", categoryRepository.findAll());
//        return "category-form";
//    }
    /**
     * ✅ Displays the budget creation form.
     *
     * @param model The model for Thymeleaf.
     * @return The budget form view.
     */
//    @GetMapping("/budget")
//    public String showBudgetForm(Model model) {
//        model.addAttribute("budgetRequest", new BudgetRequest());
//        return "budget";
//    }

    /**
     * ✅ Displays the transaction creation form.
     *
     * @param model The model for Thymeleaf.
     * @return The transaction form view.
     */
    @GetMapping("/transaction")
    public String showTransactionForm(Model model) {
        model.addAttribute("transactionRequest", new TransactionRequest());
        return "transaction";
    }
}
