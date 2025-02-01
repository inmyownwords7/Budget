//package org.java.financial.controller;
//
//import org.java.financial.dto.*;
//import org.java.financial.entity.*;
//import org.java.financial.logging.GlobalLogger;
//import org.java.financial.repository.*;
//import org.java.financial.security.AuthenticationService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.validation.Valid;
//import javax.management.relation.RoleNotFoundException;
//import java.util.Optional;
//
///**
// * **API Controller** for managing authentication, categories, budgets, and transactions.
// */
//@RestController
//@RequestMapping("/api")
//@Validated
//public class ApiController {
//
//    private final AuthenticationService authenticationService;
//    private final UserRepository userRepository;
//    private final CategoryRepository categoryRepository;
////    private final BudgetRepository budgetRepository;
//    private final TransactionRepository transactionRepository;
//
//    public ApiController(
//            AuthenticationService authenticationService,
//            UserRepository userRepository,
//            CategoryRepository categoryRepository,
//            BudgetRepository budgetRepository,
//            TransactionRepository transactionRepository) {
//        this.authenticationService = authenticationService;
//        this.userRepository = userRepository;
//        this.categoryRepository = categoryRepository;
////        this.budgetRepository = budgetRepository;
//        this.transactionRepository = transactionRepository;
//    }
//
//    // ───────────────────────────────────────────────────────────────────────────
//    // 🔹 1️⃣ USER AUTHENTICATION API
//    // ───────────────────────────────────────────────────────────────────────────
//
//    @PostMapping("/do-login")
//    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
//        try {
//            AuthResponse response = authenticationService.authenticate(request);
//            return ResponseEntity.ok(response);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PostMapping("/do-register")
//    public ResponseEntity<?> register(@Valid @RequestBody UserEntity user) {
//        try {
//            UserEntity newUser = authenticationService.register(
//                    user.getUsername(),
//                    user.getPassword(),
//                    user.getRole().getRoleName()
//            );
//            return ResponseEntity.ok(new UserResponseDTO(newUser.getUsername(), newUser.getRole().getRoleName()));
//        } catch (RuntimeException | RoleNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // ───────────────────────────────────────────────────────────────────────────
//    // 🔹 2️⃣ CATEGORY MANAGEMENT API
//    // ───────────────────────────────────────────────────────────────────────────
//
//    @PostMapping("/category/do-add")
//    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
//        if (categoryDTO.getCategoryId() != null) {
//            return ResponseEntity.badRequest().body("Category ID must be null when adding a new category.");
//        }
//
//        Category category = new Category(
//                categoryDTO.getCategoryName(),
//                categoryDTO.getCategoryDescription(),
//                categoryDTO.getCategoryType()
//        );
//
//        return ResponseEntity.ok(categoryRepository.save(category));
//    }
//
//    @PostMapping("/category/do-update")
//    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
//        Optional<Category> existingCategory = categoryRepository.findById(categoryDTO.getCategoryId());
//        if (existingCategory.isEmpty()) return ResponseEntity.badRequest().body("Category not found.");
//
//        Category category = existingCategory.get();
//        category.setCategoryName(categoryDTO.getCategoryName());
//        category.setCategoryDescription(categoryDTO.getCategoryDescription());
//        return ResponseEntity.ok(categoryRepository.save(category));
//    }
//
//    @PostMapping("/category/do-delete")
//    public ResponseEntity<?> deleteCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
//        categoryRepository.deleteById(categoryDTO.getCategoryId());
//        return ResponseEntity.ok("Category deleted.");
//    }
//
//    // ───────────────────────────────────────────────────────────────────────────
//    // 🔹 3️⃣ BUDGET MANAGEMENT API
//    // ───────────────────────────────────────────────────────────────────────────
////
////    @PostMapping("/budget/do-create")
////    public ResponseEntity<?> createBudget(@Valid @RequestBody BudgetRequest request) {
////        UserEntity user = userRepository.findById(request.getUser().getUserId())
////                .orElseThrow(() -> new IllegalArgumentException("User not found"));
////
////        Budget budget = new Budget(user, request.getCategoryName(), request.getAmount());
////        return ResponseEntity.ok(budgetRepository.save(budget));
////    }
//
//    // ───────────────────────────────────────────────────────────────────────────
//    // 🔹 4️⃣ TRANSACTION MANAGEMENT API
//    // ───────────────────────────────────────────────────────────────────────────
//
//    @PostMapping("/transaction/do-create")
//    public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionRequest request) {
//        Transaction transaction = new Transaction(
//                userRepository.findById(request.getUserId()).orElseThrow(),
//                categoryRepository.findById(request.getCategoryId()).orElseThrow(),
//                request.getAmount(),
//                TransactionType.valueOf(request.getType()),
//                request.getDescription()
//        );
//        return ResponseEntity.ok(transactionRepository.save(transaction));
//    }
//}
