package org.java.financial.controller;

import jakarta.validation.Valid;
import org.java.financial.dto.TransactionDTO;
import org.java.financial.entity.Transaction;
import org.java.financial.entity.UserEntity;
import org.java.financial.entity.Category;
import org.java.financial.enums.TransactionType;
import org.java.financial.exception.UserNotFoundException;
import org.java.financial.exception.CategoryNotFoundException;
import org.java.financial.repository.TransactionRepository;
import org.java.financial.repository.UserRepository;
import org.java.financial.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TransactionController(TransactionRepository transactionRepository,
                                 UserRepository userRepository,
                                 CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * ✅ Create a transaction using `TransactionDTO`.
     */
    @PostMapping("/do-create")
    public ResponseEntity<String> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        // ✅ Fetch UserEntity and Category before saving
        UserEntity user = userRepository.findById(transactionDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("❌ User not found with ID: " + transactionDTO.getUserId()));

        Category category = categoryRepository.findById(transactionDTO.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("❌ Category not found with ID: " + transactionDTO.getCategoryId()));

        // ✅ Convert DTO to Entity
        Transaction transaction = transactionDTO.toTransaction(user, category);

        transactionRepository.save(transaction);

        return ResponseEntity.ok("✅ Transaction successfully created!");
    }

}
