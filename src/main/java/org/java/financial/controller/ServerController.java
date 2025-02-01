package org.java.financial.controller;

import org.java.financial.dto.TransactionRequest;
import org.java.financial.dto.UserResponseDTO;
import org.java.financial.entity.*;
import org.java.financial.logging.GlobalLogger;
import org.java.financial.service.BudgetService;
import org.java.financial.service.TransactionService;
import org.java.financial.security.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;


/**
 * ServerController handles form-based requests for user registration,
 * budget creation, and transaction processing.
 */
@Controller
@RequestMapping("/api")
public class ServerController {

    private final AuthenticationService authenticationService;
    private final BudgetService budgetService;
    private final TransactionService transactionService;

    /**
     * Constructor for ServerController.
     *
     * @param authenticationService Handles user authentication and registration.
     * @param budgetService Manages budget-related operations.
     * @param transactionService Manages transaction-related operations.
     */
    public ServerController(AuthenticationService authenticationService, BudgetService budgetService, TransactionService transactionService) {
        this.authenticationService = authenticationService;
        this.budgetService = budgetService;
        this.transactionService = transactionService;
    }

    /**
     * Handles user registration form submission.
     *
     * @param user The user registration details.
     * @return ResponseEntity with the registered user's details.
     */
    @PostMapping("/do-register")
    public ResponseEntity<?> registerUser(@RequestBody UserEntity user) {
        try {
            UserEntity newUser = authenticationService.register(
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole().getRoleName()
            );

            return ResponseEntity.ok(new UserResponseDTO(newUser.getUsername(), newUser.getRole().getRoleName()));
        } catch (RuntimeException | RoleNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Handles budget creation form submission.
     *
     * @param request The budget request details.
     * @return ResponseEntity with the created budget.
     */
    @PostMapping("/do-budget")
    public ResponseEntity<?> createBudget(@RequestBody Budget request) {
        try {
            Budget budget = budgetService.createBudget(
                    request.getUser(),
                    request.getCategoryName(),
                    request.getAmount()
            );

            return ResponseEntity.ok(budget);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Handles transaction form submission.
     *
     * @param request The transaction request details.
     * @return ResponseEntity with the created transaction.
     */
    @PostMapping("/do-transaction")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest request) {
        GlobalLogger.LOGGER.info("📩 Received transaction submission: {}", request);
        try {
            Transaction transaction = transactionService.addTransaction(
                    request.getUserId(),
                    request.getCategoryId(),
                    request.getAmount(),
                    request.getType(),
                    request.getDescription()
            );
            GlobalLogger.LOGGER.info("✅ Transaction successfully created: {}", transaction);
            return ResponseEntity.ok(transaction);
        } catch (RuntimeException e) {
            GlobalLogger.LOGGER.error("❌ Failed to create transaction: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
