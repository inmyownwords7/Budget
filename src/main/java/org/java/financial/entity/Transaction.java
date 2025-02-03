package org.java.financial.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.java.financial.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ✅ Represents a financial transaction associated with a user.
 */
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User cannot be null")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Category cannot be null")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, length = 10)
    @NotNull(message = "Transaction type cannot be null")
    private TransactionType type;

    @Column(name = "amount", nullable = false)
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    @Column(name = "transaction_date", nullable = false)
    @NotNull(message = "Transaction date cannot be null")
    private LocalDate transactionDate;

    @Column(name = "description")
    private String description;

    @CreationTimestamp // ✅ Auto-sets when entity is created
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // ✅ Auto-sets when entity is updated
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * ✅ Default constructor (Required by JPA).
     */
    public Transaction() {}

    /**
     * ✅ Constructs a new Transaction.
     */
    public Transaction(UserEntity user, Category category, BigDecimal amount, TransactionType type, String description) {
        this.user = user;
        this.category = category;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.transactionDate = LocalDate.now();
    }

    // ✅ GETTERS

    public UserEntity getUser() { return user; }

    public Category getCategory() { return category; }

    public TransactionType getType() { return type; }

    public BigDecimal getAmount() { return amount; }

    public String getDescription() { return description; }

    // ✅ SETTERS

    public void setUser(UserEntity user) { this.user = user; }

    public void setCategory(Category category) { this.category = category; }

    public void setType(TransactionType type) { this.type = type; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public void setDescription(String description) { this.description = description; }

    /**
     * ✅ Returns a string representation of the transaction.
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", userId=" + (user != null ? user.getUserId() : "null") +
                ", categoryId=" + (category != null ? category.getCategoryId() : "null") +
                ", type=" + type +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
