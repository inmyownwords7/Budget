package org.java.financial.repository;

import org.java.financial.entity.Transaction;
import org.java.financial.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserUserId(Long userId); // ✅ Get transactions by User ID
    List<Transaction> findByUser(UserEntity user);
}
