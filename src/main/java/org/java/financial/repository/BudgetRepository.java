package org.java.financial.repository;

import org.java.financial.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing Budget entity operations.
 */
@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    /**
     * ✅ Finds budgets associated with a specific user.
     */
    List<Budget> findByUserUserId(Long userId);

}
