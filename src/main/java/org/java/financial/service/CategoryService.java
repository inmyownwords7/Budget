package org.java.financial.service;

import org.java.financial.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    //Defines business logic and validation before calling the repository.
    Category createCategory(String name);
    Optional<Category> getCategoryById(Long id);
    List<Category> getAllCategories();
    Category updateCategory(Long id, String name);
    void deleteCategory(Long id);
}
