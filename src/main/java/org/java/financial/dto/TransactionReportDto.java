package org.java.financial.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for Transaction Report
 */
public class TransactionReportDto {
    private Long id;
    private String category;
    private BigDecimal amount;
    private LocalDate date;

    public TransactionReportDto(Long id, String category, BigDecimal amount, LocalDate date) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
}
