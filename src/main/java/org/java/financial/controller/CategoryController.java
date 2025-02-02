package org.java.financial.controller;

import org.java.financial.dto.CategoryDTO;
import org.java.financial.entity.Category;
import org.java.financial.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/do-add")
    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        if (categoryDTO.getCategoryId() != null) {
            return ResponseEntity.badRequest().body("⚠️ Category ID must be null when adding a new category.");
        }

        Category category = new Category(
                categoryDTO.getCategoryName(),
                categoryDTO.getCategoryDescription(),
                categoryDTO.getCategoryType()
        );

        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @PostMapping("/do-update")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryDTO.getCategoryId());
        if (existingCategory.isEmpty()) return ResponseEntity.badRequest().body("⚠️ Category not found.");

        Category category = existingCategory.get();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @PostMapping("/do-delete")
    public ResponseEntity<?> deleteCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryRepository.deleteById(categoryDTO.getCategoryId());
        return ResponseEntity.ok("✅ Category deleted.");
    }
}
