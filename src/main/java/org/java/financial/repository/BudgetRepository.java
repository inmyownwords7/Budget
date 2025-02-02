package org.java.financial.repository;

import org.java.financial.entity.Budget;
import org.java.financial.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for handling budget-related queries.
 */
@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    // ✅ Find budgets by UserEntity
    List<Budget> findByUser(UserEntity user);

    // ✅ Find budgets by user ID
    List<Budget> findByUser_UserId(Long userId);
}
