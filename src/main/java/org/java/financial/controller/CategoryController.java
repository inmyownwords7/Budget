package org.java.financial.controller;

import org.java.financial.entity.Category;
import org.java.financial.logging.GlobalLogger;
import org.java.financial.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

/**
 * Controller to handle Category-related operations via JSON POST requests.
 */
@Controller // ✅ Change to Controller for Thymeleaf support
@RequestMapping("/category")
@Validated
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * ✅ Add a new category via POST
     * @param category JSON request body with category details
     * @return ResponseEntity with created category or validation errors
     */
    @PostMapping("/do-add")
    public ResponseEntity<?> addCategory(@Valid @RequestBody Category category) {
        GlobalLogger.LOGGER.info("Adding new category: {}", category.getCategoryName());

        if (category.getCategoryId() != null) {
            return ResponseEntity.badRequest().body("Category ID must be null when adding a new category.");
        }

        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(savedCategory);
    }

    /**
     * ✅ Update an existing category via POST
     * @param category JSON request body with updated category details
     * @return ResponseEntity with updated category or validation errors
     */
    @PostMapping("/do-update")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody Category category) {
        if (category.getCategoryId() == null) {
            return ResponseEntity.badRequest().body("Category ID is required for updating.");
        }

        GlobalLogger.LOGGER.info("Updating category ID: {}", category.getCategoryId());

        Optional<Category> existingCategory = categoryRepository.findById(category.getCategoryId());
        if (existingCategory.isEmpty()) {
            return ResponseEntity.badRequest().body("Category not found.");
        }

        Category updatedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(updatedCategory);
    }

    /**
     * ✅ Delete a category via POST
     * @param category JSON request body with category ID
     * @return ResponseEntity confirming deletion or error message
     */
    @PostMapping("/do-delete")
    public ResponseEntity<?> deleteCategory(@Valid @RequestBody Category category) {
        if (category.getCategoryId() == null) {
            return ResponseEntity.badRequest().body("Category ID is required for deletion.");
        }

        GlobalLogger.LOGGER.info("Deleting category ID: {}", category.getCategoryId());

        Optional<Category> existingCategory = categoryRepository.findById(category.getCategoryId());
        if (existingCategory.isEmpty()) {
            return ResponseEntity.badRequest().body("Category not found.");
        }

        categoryRepository.delete(existingCategory.get());
        return ResponseEntity.ok("Category deleted successfully.");
    }
}
