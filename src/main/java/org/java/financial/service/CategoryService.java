package org.java.financial.service;

import org.java.financial.entity.Category;
import org.java.financial.enums.CategoryType;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing categories.
 */
public interface CategoryService {

    /**
     * ✅ Creates a new category.
     */
    Category createCategory(String name, String description, CategoryType categoryType);

    /**
     * ✅ Retrieves a category by ID.
     */
    Category getCategoryById(Long categoryId);

    /**
     * ✅ Retrieves a category by name (case-insensitive).
     */
    Category getCategoryByName(String categoryName);

    /**
     * ✅ Retrieves all categories.
     */
    List<Category> getAllCategories();

    /**
     * ✅ Retrieves all categories of a specific type.
     */
    List<Category> getCategoriesByType(CategoryType categoryType);

    /**
     * ✅ Updates an existing category.
     */
    Category updateCategory(Long id, String name, String description, CategoryType categoryType);

    /**
     * ✅ Deletes a category by ID.
     */
    void deleteCategory(Long id);

    /**
     * ✅ Deletes a category by name.
     */
    void deleteCategoryByName(String categoryName);
}
