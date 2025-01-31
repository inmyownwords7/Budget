package org.java.financial.repository;

import org.java.financial.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing User entity operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by username.
     */
    Optional<User> findByUsername(String username);

    /**
     * ✅ Checks if a username already exists.
     */
    boolean existsByUsername(String username);
}
