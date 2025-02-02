package org.java.financial.dto;

import jakarta.validation.constraints.Null;
import org.java.financial.enums.CategoryType;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object (DTO) for Category-related operations.
 * Used for adding, updating, and deleting categories via JSON POST requests.
 */
public class CategoryDTO {

    @Null(message = "Category ID must be null for new categories") // Ensures ID is null when adding
    private Long categoryId;

    @NotBlank(message = "Category name cannot be empty")
    @Size(min = 3, max = 50, message = "Category name must be between 3 and 50 characters")
    private String categoryName;

    @Size(max = 255, message = "Category description cannot exceed 255 characters")
    private String categoryDescription;

    @NotNull(message = "Category type is required")
    private CategoryType categoryType;

    public CategoryDTO() {}

    public CategoryDTO(Long categoryId, String categoryName, String categoryDescription, CategoryType categoryType) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryType = categoryType;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }
}
