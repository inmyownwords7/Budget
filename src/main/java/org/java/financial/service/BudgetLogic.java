package org.java.financial.service;

import org.java.financial.entity.Budget;
import org.java.financial.entity.Category;
import org.java.financial.entity.CategoryType;
import org.java.financial.repository.BudgetRepository;
import org.java.financial.repository.CategoryRepository;
import org.java.financial.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class BudgetLogic implements BudgetService { // ✅ Correctly implements interface

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public BudgetLogic(BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Budget createBudget(User user, String categoryName, BigDecimal amount) {
        // ✅ Ensure category exists
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseGet(() -> {
                    Category newCategory = new Category(categoryName, "Auto-created", CategoryType.EXPENSE);
                    return categoryRepository.save(newCategory);
                });

        // ✅ Create budget with dates
        Budget budget = new Budget(user, category, amount, LocalDate.now(), LocalDate.now().plusMonths(1));
        return budgetRepository.save(budget);
    }

    @Override
    public List<Budget> getUserBudgets(Long userId) {
        return budgetRepository.findByUserUserId(userId);
    }
}
