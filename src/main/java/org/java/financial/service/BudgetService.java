package org.java.financial.service;

import org.java.financial.entity.Budget;
import org.java.financial.entity.UserEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for managing budgets.
 * <p>
 * This interface provides methods to create and retrieve budgets for users.
 * </p>
 */
public interface BudgetService {

    /**
     * Creates a new budget for a user in a given category.
     *
     * @param user         The user for whom the budget is being created.
     * @param categoryName The name of the budget category.
     * @param amount       The budgeted amount.
     * @return The created {@link Budget} entity.
     */
    Budget createBudget(UserEntity user, String categoryName, BigDecimal amount);

    List<Budget> getUsername(String username);

    /**
     * Retrieves all budgets associated with a specific user.
     *
     * @param userId The ID of the user whose budgets are to be retrieved.
     * @return A list of budgets for the specified user.
     */
}
