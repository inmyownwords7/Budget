package org.java.financial.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, length = 10)
    private TransactionType type; // INCOME or EXPENSE

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Transaction(User user, Category category, BigDecimal amount, TransactionType transactionType, String description) {
        this.user = user;
        this.category = category;
        this.amount = amount;
        this.type = transactionType;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Auto-Update Timestamps
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Adjust Amount Based on Type (Prevents modifying values unexpectedly)
    private BigDecimal adjustAmount(BigDecimal amount, TransactionType type) {
        if (amount == null || type == null) return amount;

        // Convert to negative for EXPENSE
        if (type == TransactionType.EXPENSE && amount.compareTo(BigDecimal.ZERO) > 0) {
            return amount.negate();
        }
        // Convert to positive for INCOME
        if (type == TransactionType.INCOME && amount.compareTo(BigDecimal.ZERO) < 0) {
            return amount.abs();
        }
        return amount;
    }

    // Constructors
    public Transaction() {}

    public Transaction(User user, Category category, BigDecimal amount, TransactionType type, LocalDate transactionDate, String description) {
        if (amount == null) throw new IllegalArgumentException("Transaction amount cannot be null.");
        if (transactionDate == null) throw new IllegalArgumentException("Transaction date cannot be null.");

        this.user = user;
        this.category = category;
        this.amount = adjustAmount(amount, type); // Ensure proper adjustment
        this.type = type;
        this.transactionDate = transactionDate;
        this.description = description;
    }

    // Getters and Setters
    public Long getTransactionId() { return transactionId; }

    public User getUser() { return user; }

    public Category getCategory() { return category; }

    public TransactionType getType() { return type; }

    public BigDecimal getAmount() { return amount; }

    public LocalDate getTransactionDate() { return transactionDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public String getDescription() { return description; }

    public void setAmount(BigDecimal amount) {
        if (amount == null) throw new IllegalArgumentException("Transaction amount cannot be null.");
        this.amount = adjustAmount(amount, this.type); // Adjust amount based on type
    }

    public void setType(TransactionType type) {
        this.type = type;
        this.amount = adjustAmount(this.amount, type); // Adjust amount based on type
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Set category if needed (added setter)
    public void setCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        this.category = category;
    }

    // Equals, HashCode, ToString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", user=" + (user != null ? user.getUserId() : "null") +
                ", category=" + (category != null ? category.getCategoryId() : "null") +
                ", type=" + type +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
