package org.java.financial.dto;

import java.math.BigDecimal;

/**
 * DTO for handling transaction form submissions.
 */
public class TransactionRequest {
    private Long userId;
    private Long categoryId;
    private BigDecimal amount;
    private String type;
    private String description;

    public Long getUserId() {
        return userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
