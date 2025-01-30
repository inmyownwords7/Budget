package org.java.financial.service;

import org.java.financial.entity.Transaction;
import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    Transaction addTransaction(Long userId, Long categoryId, BigDecimal amount, String type, String description);
    List<Transaction> getUserTransactions(Long userId);
    Transaction getTransactionById(Long transactionId);
    Transaction updateTransaction(Long transactionId, Long categoryId, BigDecimal amount, String type, String description);
    void deleteTransaction(Long transactionId);
}
