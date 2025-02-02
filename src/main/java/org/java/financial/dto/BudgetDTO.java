package org.java.financial.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for Budget data transfer.
 */
public class BudgetDTO {

    private Long budgetId;
    private String username;
    private String categoryName;
    private BigDecimal amount;
    private LocalDate startDate;
    private LocalDate endDate;

    public BudgetDTO(Long budgetId, String username, String categoryName, BigDecimal amount, LocalDate startDate, LocalDate endDate) {
        this.budgetId = budgetId;
        this.username = username;
        this.categoryName = categoryName;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // ✅ Getters and Setters
    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
}
