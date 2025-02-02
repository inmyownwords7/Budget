package org.java.financial.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long budgetId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user; // ✅ Links Budget to UserEntity

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    public Budget(UserEntity user, Category category, BigDecimal amount, LocalDate now, LocalDate localDate) {
        this.user = user;
        this.category = category;
        this.amount = amount;
        this.startDate = now;
        this.endDate = localDate;
    }

    public Budget() {

    }

    // ✅ Constructors, Getters, and Setters

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Long getBudgetId() {
        return budgetId;
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

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
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
