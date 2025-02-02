package org.java.financial.service;

import org.java.financial.dto.BudgetDTO;
import java.math.BigDecimal;

/**
 * Service interface for managing budgets.
 */
public interface BudgetService {

    /**
     * ✅ Creates a new budget for a user in a given category.
     *
     * @param username     The username for whom the budget is being created.
     * @param categoryName The name of the budget category.
     * @param amount       The budgeted amount.
     * @return The created {@link BudgetDTO}.
     */
    BudgetDTO createBudget(String username, String categoryName, BigDecimal amount);
}
