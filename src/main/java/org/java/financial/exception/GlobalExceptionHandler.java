package org.java.financial.exception;

import org.java.financial.logging.GlobalLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * 🌍 Global Exception Handler
 * ✅ Handles application-wide exceptions and returns structured error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ❌ Handle User Already Exists Exception (409 Conflict)
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        GlobalLogger.LOGGER.warn("⚠️ User registration failed: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * ❌ Handle Invalid Arguments (400 Bad Request)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        GlobalLogger.LOGGER.warn("⚠️ Invalid request: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * ❌ Handle Generic Exceptions (500 Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        GlobalLogger.LOGGER.error("❌ Unhandled exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "An unexpected error occurred."));
    }

    /**
     * ❌ Handle Role Not Found (404 Not Found)
     */
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRoleNotFoundException(RoleNotFoundException ex) {
        GlobalLogger.LOGGER.error("🚫 Role error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * ❌ Handle Transaction Not Found (404 Not Found)
     */
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTransactionNotFoundException(TransactionNotFoundException ex) {
        GlobalLogger.LOGGER.error("🔎 Transaction not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * ❌ Handle Access Denied (403 Forbidden)
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDenied(AccessDeniedException ex) {
        GlobalLogger.LOGGER.warn("🚫 Access denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("error", "You do not have permission to access this resource."));
    }

    /**
     * ❌ Handle User Not Found (404 Not Found)
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
        GlobalLogger.LOGGER.error("🔎 User not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * ❌ Handle Category Not Found (404 Not Found)
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        GlobalLogger.LOGGER.error("🔎 Category not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * ❌ Handle Invalid Credentials (401 Unauthorized)
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        GlobalLogger.LOGGER.error("🔑 Invalid credentials: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid username or password."));
    }
}
