package org.java.financial.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // ✅ Each budget belongs to one user

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount; // ✅ Total budget allocated

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal spent = BigDecimal.ZERO; // ✅ Default to 0

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal remaining; // ✅ Auto-calculated field

    @Column(nullable = false)
    private LocalDate startDate; // ✅ Budget start date

    @Column(nullable = false)
    private LocalDate endDate; // ✅ Budget end date

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // ✅ Created timestamp

    @Column(nullable = false)
    private LocalDateTime updatedAt; // ✅ Updated timestamp

    // 🔹 Auto-update timestamps
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // 🔹 Auto-calculate remaining budget
    public void updateRemaining() {
        this.remaining = this.amount.subtract(this.spent);
    }

    // ✅ Constructors
    public Budget() {
    }

    public Budget(User user, Category category, BigDecimal amount, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.category = category;
        this.amount = amount;
        this.spent = BigDecimal.ZERO;
        this.remaining = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
        updateRemaining();
    }

    public BigDecimal getSpent() {
        return spent;
    }

    public void setSpent(BigDecimal spent) {
        this.spent = spent;
        updateRemaining();
    }

    public BigDecimal getRemaining() {
        return remaining;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
