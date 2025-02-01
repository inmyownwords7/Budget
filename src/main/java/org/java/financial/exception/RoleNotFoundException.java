package org.java.financial.exception;

/**
 * Exception thrown when a specified role is not found in the database.
 */
public class RoleNotFoundException extends RuntimeException {

    /**
     * Constructs a new RoleNotFoundException with a custom message.
     *
     * @param message The error message.
     */
    public RoleNotFoundException(String message) {
        super(message);
    }
}
