package org.java.financial.service;

import org.java.financial.entity.Category;
import org.java.financial.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(String name) {
        if (categoryRepository.existsByCategoryName(name)) {
            throw new RuntimeException("Category already exists");
        }
        Category category = new Category();
        category.setCategoryName(name);
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long id, String name) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setCategoryName(name);
                    return categoryRepository.save(category);
                }).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
