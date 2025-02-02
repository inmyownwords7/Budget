package org.java.financial.implementation;

import org.java.financial.entity.Category;
import org.java.financial.enums.CategoryType;
import org.java.financial.exception.CategoryNotFoundException;
import org.java.financial.repository.CategoryRepository;
import org.java.financial.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of CategoryService.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * ✅ Creates a new category.
     */
    @Override
    public Category createCategory(String name, String description, CategoryType categoryType) {
        if (categoryRepository.existsByCategoryName(name)) {
            throw new RuntimeException("Category already exists: " + name);
        }
        Category category = new Category(name, description, categoryType);
        return categoryRepository.save(category);
    }

    /**
     * ✅ Retrieves a category by ID.
     */
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category ID " + id + " not found."));
    }

    /**
     * ✅ Retrieves a category by name (case-insensitive).
     */
    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByCategoryNameIgnoreCase(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + name));
    }

    /**
     * ✅ Retrieves all categories.
     */
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * ✅ Retrieves all categories of a specific type (EXPENSE, INCOME, ASSET).
     */
    @Override
    public List<Category> getCategoriesByType(CategoryType categoryType) {
        return categoryRepository.findByCategoryType(categoryType);
    }

    /**
     * ✅ Updates an existing category.
     */
    @Override
    public Category updateCategory(Long id, String name, String description, CategoryType categoryType) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setCategoryName(name);
                    category.setCategoryDescription(description);
                    category.setCategoryType(categoryType);
                    return categoryRepository.save(category);
                }).orElseThrow(() -> new CategoryNotFoundException("Category not found: " + id));
    }

    /**
     * ✅ Deletes a category by ID.
     */
    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found: " + id);
        }
        categoryRepository.deleteById(id);
    }

    /**
     * ✅ Deletes a category by name.
     */
    @Override
    public void deleteCategoryByName(String name) {
        if (!categoryRepository.existsByCategoryName(name)) {
            throw new CategoryNotFoundException("Category not found: " + name);
        }
        categoryRepository.deleteByCategoryName(name);
    }
}
