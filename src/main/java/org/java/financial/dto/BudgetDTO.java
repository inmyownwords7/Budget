package org.java.financial.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO for Budget data transfer.
 */
public class BudgetDTO {

    private Long budgetId;

    @NotNull(message = "{error.budget.user.required}")
    private Long userId;

    @NotNull(message = "{error.budget.category.required}")
    private Long categoryId;

    @Min(value = 0, message = "{error.budget.amount.min}")
    private BigDecimal amount;

    // ✅ Constructors
    public BudgetDTO() {}

    public BudgetDTO(Long budgetId, Long userId, Long categoryId, BigDecimal amount) {
        this.budgetId = budgetId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
    }

    // ✅ Getters and Setters
    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
