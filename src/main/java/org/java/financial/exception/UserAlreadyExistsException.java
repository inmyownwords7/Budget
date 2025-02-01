package org.java.financial.exception;

/**
 * Exception thrown when a user tries to register with a username that already exists.
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new UserAlreadyExistsException with the specified message.
     *
     * @param message The error message.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
