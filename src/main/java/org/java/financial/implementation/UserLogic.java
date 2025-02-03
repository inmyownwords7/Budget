package org.java.financial.implementation;

import org.java.financial.entity.*;
import org.java.financial.enums.CategoryType;
import org.java.financial.exception.*;
import org.java.financial.exception.UserNotFoundException;
import org.java.financial.logging.GlobalLogger;
import org.java.financial.repository.*;
import org.java.financial.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * **UserLogic Service Implementation**
 * <p>
 * This class implements {@link UserService} and provides logic for user registration,
 * password encoding, role assignment, and default budget and category setup.
 * </p>
 */
@Service
public class UserLogic implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * **Constructor for UserLogic**
     *
     * @param userRepository      Repository for managing user-related database operations.
     * @param roleRepository      Repository for retrieving user roles.
     * @param budgetRepository    Repository for handling budget-related operations.
     * @param categoryRepository  Repository for category management.
     * @param passwordEncoder     Encoder for securely hashing user passwords.
     */
    public UserLogic(UserRepository userRepository, RoleRepository roleRepository,
                     BudgetRepository budgetRepository, CategoryRepository categoryRepository,
                     PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * **Registers a new user in the system.**
     * <p>
     * This method:
     * - Checks if the user already exists.
     * - Finds and assigns the correct role.
     * - Encodes the password before saving.
     * - Assigns a default budget and category.
     * </p>
     *
     * @param username The username of the new user.
     * @param password The raw password (to be encoded).
     * @param role     The role assigned to the user (e.g., "ROLE_USER").
     * @return The registered {@link UserEntity} object.
     * @throws RoleNotFoundException If the specified role does not exist.
     * @throws UserAlreadyExistsException If the username is already taken.
     */
    @Override
    public UserEntity registerUser(String username, String password, String role) throws RoleNotFoundException {
        if (userRepository.existsByUsername(username)) {
            GlobalLogger.LOGGER.warn("⚠️ User '{}' already exists, registration failed.", username);
            throw new UserAlreadyExistsException("User already exists");
        }

        // ✅ Find role (or fail if it doesn't exist)
        Role userRole = roleRepository.findByRoleName(role)
                .orElseThrow(() -> new RoleNotFoundException("Role '" + role + "' not found."));

        // ✅ Encode password before saving user
        String encodedPassword = passwordEncoder.encode(password);

        // ✅ Create user entity
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRole(userRole);
        user = userRepository.save(user);

        GlobalLogger.LOGGER.info("✅ New user '{}' registered successfully with role '{}'", user.getUsername(), user.getRole().getRoleName());

        // ✅ Ensure default category exists
        Category defaultCategory = categoryRepository.findByCategoryName("General Expenses")
                .orElseGet(() -> {
                    GlobalLogger.LOGGER.info("📌 Category 'General Expenses' not found. Creating it.");
                    return categoryRepository.save(new Category("General Expenses", "Default category", CategoryType.EXPENSE));
                });

        // ✅ Assign a default budget linked to the category
        Budget defaultBudget = new Budget(user, defaultCategory, new BigDecimal("100.00"), LocalDate.now(), LocalDate.now().plusMonths(1));
        budgetRepository.save(defaultBudget);

        GlobalLogger.LOGGER.info("📊 Assigned default budget of $100.00 to user '{}'", user.getUsername());

        return user;
    }

    /**
     * ✅ Fetch UserEntity by username.
     *
     * @param username
     */
    @Override
    public UserEntity findUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
    }

    /**
     * **Checks if a user exists by username.**
     *
     * @param username The username to check.
     * @return `true` if the user exists, `false` otherwise.
     */
    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * @param username
     * @param password
     * @return
     */


    /**
     * **Validates user credentials.**
     * <p>
     * This method is a placeholder and is not currently implemented.
     * Future versions should check if the provided password matches the stored hashed password.
     * </p>
     *
     * @param username The username to validate.
     * @param password The password to check against the stored hash.
     * @return `true` if valid credentials, `false` otherwise.
     */
    /**
     * ✅ Assigns a default budget to a user.
     */
    private void assignDefaultBudget(UserEntity user) {
        Category defaultCategory = categoryRepository.findByCategoryName("General Expenses")
                .orElseGet(() -> categoryRepository.save(new Category("General Expenses", "Default category", CategoryType.EXPENSE)));

        Budget defaultBudget = new Budget(user, defaultCategory, new BigDecimal("100.00"), LocalDate.now(), LocalDate.now().plusMonths(1));
        budgetRepository.save(defaultBudget);
    }
}
