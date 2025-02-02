package org.java.financial.entity;

import jakarta.persistence.*;
import org.java.financial.enums.CategoryType;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false, unique = true)
    private String categoryName;

    private String categoryDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private CategoryType categoryType;

    public Category() {}

    public Category(String categoryName, String categoryDescription, CategoryType categoryType) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryType = categoryType;
    }

    public Long getCategoryId() {
        return categoryId;
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
