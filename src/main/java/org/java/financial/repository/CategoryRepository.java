package org.java.financial.repository;

import org.java.financial.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Category entity operations.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * ✅ Checks if a category exists by name.
     */
    boolean existsByCategoryName(String categoryName);

    /**
     * ✅ Finds a category by name (returns Optional to handle empty results safely).
     */
    Optional<Category> findByCategoryName(String categoryName);

    /**
     * ✅ Retrieves all categories of a specific type (INCOME, EXPENSE, ASSET).
     */
    List<Category> findByCategoryType(String categoryType);

    /**
     * ✅ Deletes a category by name.
     */
    void deleteByCategoryName(String categoryName);
}
