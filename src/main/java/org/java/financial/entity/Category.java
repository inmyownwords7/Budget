package org.java.financial.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Column(nullable = false)
    private String categoryName;
    @Column(name = "category_description")
    private String categoryDescription;

    @Enumerated(EnumType.STRING) // ✅ Store enum as a string in the database
    @Column(nullable = false, length = 10)
    private CategoryType categoryType;

    //TODO Make a BudgetList
    public Category() {

    }

    public Category(String categoryName, String categoryDescription, CategoryType categoryType) {
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

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryDescription='" + categoryDescription + '\'' +
                ", categoryType='" + categoryType + '\'' +
                '}';
    }
}
