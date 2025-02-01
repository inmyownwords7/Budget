//package org.java.financial.dto;
//
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import org.java.financial.entity.UserEntity;
//import java.math.BigDecimal;
//
///**
// * DTO for handling budget form submissions.
// */
//public class BudgetRequest {
//
//    @NotNull(message = "User ID cannot be null")
//    private Long requestId; // ✅ Only send userId, not full UserEntity
//
//    @NotNull(message = "User cannot be null")
//    private UserEntity user;
//
//    @NotBlank(message = "Category name cannot be empty")
//    private String categoryName;
//
//    @NotNull(message = "Amount is required")
//    @Min(value = 1, message = "Amount must be greater than zero")
//    private BigDecimal amount;
//
//    // ✅ No-args constructor (Needed for serialization)
//    public BudgetRequest() {}
//
//    // ✅ All-args constructor
//    public BudgetRequest(Long requestId, UserEntity user, String categoryName, BigDecimal amount) {
//        this.user = user;
//        this.categoryName = categoryName;
//        this.amount = amount;
//    }
//    // ✅ All-args constructor
//    public BudgetRequest(UserEntity user, String categoryName, BigDecimal amount) {
//        this.user = user;
//        this.categoryName = categoryName;
//        this.amount = amount;
//    }
//
//    public Long getRequestId() {
//        return requestId;
//    }
//
//    public void setRequestId(Long requestId) {
//        this.requestId = requestId;
//    }
//
//    // ✅ Getters
//    public UserEntity getUser() {
//        return user;
//    }
//
//    public String getCategoryName() {
//        return categoryName;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    // ✅ Setters
//    public void setUser(UserEntity user) {
//        this.user = user;
//    }
//
//    public void setCategoryName(String categoryName) {
//        this.categoryName = categoryName;
//    }
//
//    public void setAmount(BigDecimal amount) {
//        this.amount = amount;
//    }
//}
