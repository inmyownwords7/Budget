package org.java.financial.controller;

import jakarta.validation.Valid;
import org.java.financial.entity.Category;
import org.java.financial.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // ✅ Ensure it's @Controller, not @RestController
public class FormulaController {

    private final CategoryRepository categoryRepository;

    public FormulaController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // ✅ Load the category form
    @GetMapping("/form")
    public String showCategoryForm(Model model) {
        model.addAttribute("category", new Category()); // ✅ Correctly initialize Category
        model.addAttribute("categories", categoryRepository.findAll());
        return "category-form"; // ✅ Thymeleaf template
    }

    // ✅ Handle Form Submission
    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("category") Category category,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            // Return form with validation messages
            model.addAttribute("categories", categoryRepository.findAll());
            return "category-form";
        }

        categoryRepository.save(category);
        return "redirect:/form"; // ✅ Redirect to form after success
    }
}
