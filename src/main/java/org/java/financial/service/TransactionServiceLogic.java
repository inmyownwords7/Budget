package org.java.financial.service;

import org.java.financial.entity.*;
import org.java.financial.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class TransactionServiceLogic implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TransactionServiceLogic(TransactionRepository transactionRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Transaction addTransaction(Long userId, Long categoryId, BigDecimal amount, String type, String description) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        // Parse transaction type
        TransactionType transactionType = getTransactionType(type);

        // Create and save the transaction
        Transaction transaction = new Transaction(user, category, amount, transactionType, description);
        return transactionRepository.save(transaction);
    }

    private TransactionType getTransactionType(String type) {
        try {
            return TransactionType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid transaction type. Allowed values: INCOME, EXPENSE");
        }
    }

    @Override
    public List<Transaction> getUserTransactions(Long userId) {
        return transactionRepository.findByUserUserId(userId);
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
    }

    @Override
    public Transaction updateTransaction(Long transactionId, Long categoryId, BigDecimal amount, String type, String description) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        // Parse transaction type
        TransactionType transactionType = getTransactionType(type);

        // Update the transaction fields
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setType(transactionType);
        transaction.setDescription(description);

        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        transactionRepository.delete(transaction);
    }
}
