package org.java.financial.repository;

import org.java.financial.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing User entity operations.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Finds a user by username.
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * ✅ Checks if a username already exists.
     */
    boolean existsByUsername(String username);

    List<UserEntity> username(String username);
}
