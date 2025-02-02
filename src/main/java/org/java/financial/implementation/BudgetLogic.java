package org.java.financial.implementation;

import org.java.financial.dto.BudgetDTO;
import org.java.financial.entity.Budget;
import org.java.financial.entity.Category;
import org.java.financial.entity.UserEntity;
import org.java.financial.enums.CategoryType;
import org.java.financial.repository.BudgetRepository;
import org.java.financial.repository.CategoryRepository;
import org.java.financial.repository.UserRepository;
import org.java.financial.logging.GlobalLogger;
import org.java.financial.service.BudgetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Implementation of BudgetService.
 */
@Service
@Transactional
public class BudgetLogic implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public BudgetLogic(BudgetRepository budgetRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    /**
     * ✅ Creates a new budget for a user in a given category.
     */
    @Override
    public BudgetDTO createBudget(String username, String categoryName, BigDecimal amount) {
        GlobalLogger.LOGGER.info("Creating budget for user '{}' in category '{}'", username, categoryName);

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            GlobalLogger.LOGGER.warn("Attempted to create budget with invalid amount: {}", amount);
            throw new IllegalArgumentException("Budget amount must be greater than 0.");
        }

        // Ensure user exists
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

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

        // ✅ Return BudgetDTO instead of Budget entity
        return new BudgetDTO(
                savedBudget.getBudgetId(),
                user.getUsername(),
                category.getCategoryName(),
                savedBudget.getAmount(),
                savedBudget.getStartDate(),
                savedBudget.getEndDate()
        );
    }
}
