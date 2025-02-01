package org.java.financial.repository;

import org.java.financial.entity.Budget;
import org.java.financial.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for handling budget-related database operations.
 */
@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    // ✅ Fix: Use `userEntity` instead of `user`
}
