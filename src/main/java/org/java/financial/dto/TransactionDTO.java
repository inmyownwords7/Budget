package org.java.financial.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.java.financial.entity.Category;
import org.java.financial.entity.Transaction;
import org.java.financial.entity.UserEntity;
import org.java.financial.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for Transaction data transfer.
 */
public class TransactionDTO {

    private Long transactionId;

    @NotNull(message = "{error.transaction.user.required}")
    private Long userId;

    @NotNull(message = "{error.transaction.category.required}")
    private Long categoryId;

    @NotNull(message = "{error.transaction.amount.required}")
    @Min(value = 0, message = "{error.transaction.amount.min}")
    private BigDecimal amount;

    @NotBlank(message = "{error.transaction.type.required}")
    private String transactionType; // Example: "INCOME" or "EXPENSE"

    private String description;

    private LocalDateTime transactionDate; // Defaults to now

    // ✅ Constructors
    public TransactionDTO() {}

    public TransactionDTO(Long transactionId, Long userId, Long categoryId, BigDecimal amount, String transactionType, String description, LocalDateTime transactionDate) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    // ✅ Getters and Setters
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
    public Transaction toTransaction(UserEntity user, Category category) {
        return new Transaction(
                user,
                category,
                this.amount,
                TransactionType.valueOf(this.transactionType.toUpperCase()), // Convert String to Enum
                this.description
        );
    }

}
