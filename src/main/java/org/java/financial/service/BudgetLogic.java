package org.java.financial.service;

import org.java.financial.entity.*;
import org.java.financial.repository.*;
import org.java.financial.logging.GlobalLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Service class for handling budget operations.
 * <p>
 * This service allows users to create, retrieve, and manage budgets for different expense categories.
 * </p>
 */
@Service
@Transactional
public class BudgetLogic implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a BudgetLogic service with the required dependencies.
     *
     * @param budgetRepository   The repository for budget operations.
     * @param categoryRepository The repository for category management.
     * @param userRepository     The repository for user data retrieval.
     */
    public BudgetLogic(BudgetRepository budgetRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new budget for a user in a given category.
     *
     * @param user         The user creating the budget.
     * @param categoryName The name of the category.
     * @param amount       The budget amount.
     * @return The created {@link Budget} entity.
     * @throws IllegalArgumentException If the amount is invalid or the user is not found.
     */
    @Override
    public Budget createBudget(UserEntity user, String categoryName, BigDecimal amount) {
        GlobalLogger.LOGGER.info("Creating budget for user ID: {} in category: {}", user.getUserId(), categoryName);

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            GlobalLogger.LOGGER.warn("Attempted to create budget with invalid amount: {}", amount);
            throw new IllegalArgumentException("Budget amount must be greater than 0.");
        }

        // Ensure category exists
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseGet(() -> {
                    GlobalLogger.LOGGER.info("Category '{}' not found. Creating new category.", categoryName);
                    Category newCategory = new Category(categoryName, "Auto-created", CategoryType.EXPENSE);
                    return categoryRepository.save(newCategory);
                });

        // Create budget with default one-month duration
        Budget budget = new Budget(user, category, amount, LocalDate.now(), LocalDate.now().plusMonths(1));
        Budget savedBudget = budgetRepository.save(budget);

        GlobalLogger.LOGGER.info("Budget successfully created: {}", savedBudget);
        return savedBudget;
    }

    /**
     * Retrieves all budgets associated with a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of budgets belonging to the user.
     * @throws IllegalArgumentException If the user is not found.
     */

    /**
     * @param username
     * @return
     */
    @Override
    public List<Budget> getUsername(String username) {
        return List.of();
    }
}
