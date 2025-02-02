package org.java.financial.controller;

import org.java.financial.dto.TransactionRequest;
import org.java.financial.entity.Transaction;
import org.java.financial.entity.TransactionType;
import org.java.financial.repository.CategoryRepository;
import org.java.financial.repository.TransactionRepository;
import org.java.financial.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

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

    @PostMapping("/do-create")
    public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionRequest request) {
        Transaction transaction = new Transaction(
                userRepository.findById(request.getUserId()).orElseThrow(),
                categoryRepository.findById(request.getCategoryId()).orElseThrow(),
                request.getAmount(),
                TransactionType.valueOf(request.getType()),
                request.getDescription()
        );
        return ResponseEntity.ok(transactionRepository.save(transaction));
    }
}
