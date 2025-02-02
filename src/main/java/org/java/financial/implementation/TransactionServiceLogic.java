package org.java.financial.implementation;

import org.java.financial.entity.*;
import org.java.financial.enums.TransactionType;
import org.java.financial.repository.*;
import org.java.financial.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class TransactionServiceLogic implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceLogic.class);

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
        logger.info("Adding transaction for userId: {} with amount: {}", userId, amount);

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            logger.warn("Invalid transaction amount: {}", amount);
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        TransactionType transactionType = getTransactionType(type);

        Transaction transaction = new Transaction(user, category, amount, transactionType, description);
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getUserTransactions(Long userId) {
        logger.info("Fetching transactions for userId: {}", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return transactionRepository.findByUser(user);
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
    }

    @Override
    public Transaction updateTransaction(Long transactionId, Long categoryId, BigDecimal amount, String type, String description) {
        logger.info("Updating transactionId: {}", transactionId);

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            logger.warn("Invalid transaction amount: {}", amount);
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        TransactionType transactionType = getTransactionType(type);

        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setType(transactionType);
        transaction.setDescription(description);

        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        logger.info("Deleting transactionId: {}", transactionId);

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        transactionRepository.delete(transaction);
    }

    private TransactionType getTransactionType(String type) {
        try {
            return TransactionType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.error("Invalid transaction type: {}", type);
            throw new IllegalArgumentException("Invalid transaction type. Allowed values: INCOME, EXPENSE");
        }
    }
}
