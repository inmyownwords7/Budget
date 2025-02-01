package org.java.financial.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity representing a budget.
 */
@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    /**
     * Default constructor.
     */
    public Budget(String username) {

    }

    public Budget() {

    }

    public String getUsername() {
        return user != null ? user.getUsername() : null;
    }

    /**
     * Constructor to create a Budget instance.
     *
     * @param user     The user associated with the budget.
     * @param categoryName The budget category.
     * @param amount   The budgeted amount.
     */

    public Budget(UserEntity user, String categoryName, BigDecimal amount) {
        this.user = user;
        this.categoryName = categoryName;
        this.amount = amount;
    }

    public Budget(UserEntity user, Category category, BigDecimal amount) {
        this.user = user;
        this.category = category;
        this.amount = amount;
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now().plusMonths(1); // Default 1-month budget
    }

    public Budget(UserEntity user, Category category, BigDecimal amount, LocalDate now, LocalDate localDate) {
        this.user = user;
        this.category = category;
        this.amount = amount;
        this.startDate = now;
        this.endDate = localDate;
    }



    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // ✅ Getters & Setters
    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public Category getCategory() {
        return category;
    }

    public Long getCategoryId() {
        return category.getCategoryId(); // ✅ Get the ID from the category object
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
