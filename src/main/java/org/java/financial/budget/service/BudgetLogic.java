package org.java.financial.budget.service;

public class BudgetLogic implements BudgetServiceInterface{
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Budget createBudget(User user, String categoryName, BigDecimal amount) {
        // ✅ Check if category exists, otherwise create it
        Category category = categoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    Category newCategory = new Category(categoryName, null); // Type will be set later
                    return categoryRepository.save(newCategory);
                });

        Budget budget = new Budget(user, category, amount, null);
        return budgetRepository.save(budget);
    }

    @Override
    public List<Budget> getUserBudgets(Long userId) {
        return budgetRepository.findByUserId(userId);
    }
}
