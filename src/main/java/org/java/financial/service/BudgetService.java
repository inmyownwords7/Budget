package org.java.financial.service;

import org.java.financial.entity.Budget;
import org.java.financial.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface BudgetService {
    Budget createBudget(User user, String categoryName, BigDecimal amount); // ✅ Must match exactly

    List<Budget> getUserBudgets(Long userId); // ✅ Must match exactly
}
