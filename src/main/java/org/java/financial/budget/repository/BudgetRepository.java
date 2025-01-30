package org.java.financial.budget.repository;

import org.java.financial.budget.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserId(Long userId); // ✅ Find all budgets for a user
}
