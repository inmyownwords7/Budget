package org.java.financial.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a financial transaction associated with a user.
 * <p>
 * This entity tracks income and expenses categorized under specific categories.
 * Transactions belong to a user and are classified as either an income or an expense.
 * </p>
 */
@Entity
@Table(name = "transactions")
public class Transaction {

    /**
     * Unique identifier for the transaction.
     * Auto-generated using an identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    /**
     * The user associated with this transaction.
     * <p>
     * Many transactions can belong to a single user.
     * Uses {@link FetchType#LAZY} to optimize performance.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * The category assigned to this transaction.
     * <p>
     * Each transaction belongs to a specific category (e.g., Rent, Salary).
     * Uses {@link FetchType#LAZY} to avoid unnecessary database queries.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /**
     * The type of transaction, either INCOME or EXPENSE.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, length = 10)
    private TransactionType type;

    /**
     * The monetary amount of the transaction.
     */
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    /**
     * The date when the transaction was made.
     */
    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    /**
     * Optional description of the transaction.
     */
    @Column(name = "description", length = 255)
    private String description;

    /**
     * The timestamp when the transaction was created.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * The timestamp when the transaction was last updated.
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Default constructor required by JPA.
     */
    public Transaction() {}

    /**
     * Constructs a new Transaction with the specified details.
     *
     * @param user        The user associated with this transaction.
     * @param category    The category of the transaction.
     * @param amount      The amount of the transaction.
     * @param type        The type of transaction (INCOME or EXPENSE).
     * @param description Optional description of the transaction.
     */
    public Transaction(UserEntity user, Category category, BigDecimal amount, TransactionType type, String description) {
        setUser(user);
        setCategory(category);
        setAmount(amount);
        setType(type);
        this.description = description;
        this.transactionDate = LocalDate.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    /**
     * Handles automatic timestamps when creating a transaction.
     * Ensures {@code createdAt} and {@code updatedAt} are correctly set.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    /**
     * Handles automatic timestamps when updating a transaction.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ✅ GETTERS

    /**
     * Gets the transaction ID.
     *
     * @return The transaction ID.
     */
    public Long getTransactionId() { return transactionId; }

    /**
     * Gets the user associated with this transaction.
     *
     * @return The user entity.
     */
    public UserEntity getUser() { return user; }

    /**
     * Gets the category assigned to this transaction.
     *
     * @return The category entity.
     */
    public Category getCategory() { return category; }

    /**
     * Gets the type of the transaction (INCOME or EXPENSE).
     *
     * @return The transaction type.
     */
    public TransactionType getType() { return type; }

    /**
     * Gets the amount of the transaction.
     *
     * @return The transaction amount.
     */
    public BigDecimal getAmount() { return amount; }

    /**
     * Gets the date when the transaction occurred.
     *
     * @return The transaction date.
     */
    public LocalDate getTransactionDate() { return transactionDate; }

    /**
     * Gets the description of the transaction.
     *
     * @return The transaction description.
     */
    public String getDescription() { return description; }

    /**
     * Gets the timestamp when the transaction was created.
     *
     * @return The creation timestamp.
     */
    public LocalDateTime getCreatedAt() { return createdAt; }

    /**
     * Gets the timestamp when the transaction was last updated.
     *
     * @return The last update timestamp.
     */
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ✅ SETTERS

    /**
     * Sets the user associated with this transaction.
     *
     * @param user The user entity.
     * @throws IllegalArgumentException if {@code user} is null.
     */
    public void setUser(UserEntity user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        this.user = user;
    }

    /**
     * Sets the category assigned to this transaction.
     *
     * @param category The category entity.
     * @throws IllegalArgumentException if {@code category} is null.
     */
    public void setCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null.");
        }
        this.category = category;
    }

    /**
     * Sets the type of the transaction.
     *
     * @param type The transaction type (INCOME or EXPENSE).
     * @throws IllegalArgumentException if {@code type} is null.
     */
    public void setType(TransactionType type) {
        if (type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null.");
        }
        this.type = type;
    }

    /**
     * Sets the amount of the transaction.
     *
     * @param amount The transaction amount.
     * @throws IllegalArgumentException if {@code amount} is null or less than zero.
     */
    public void setAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null.");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        this.amount = amount;
    }

    /**
     * Sets the transaction date.
     *
     * @param transactionDate The transaction date.
     * @throws IllegalArgumentException if {@code transactionDate} is null.
     */
    public void setTransactionDate(LocalDate transactionDate) {
        if (transactionDate == null) {
            throw new IllegalArgumentException("Transaction date cannot be null.");
        }
        this.transactionDate = transactionDate;
    }

    /**
     * Sets the description of the transaction.
     *
     * @param description The transaction description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the transaction.
     *
     * @return A formatted string containing transaction details.
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
