package org.java.financial.service;

import org.java.financial.entity.UserEntity;
import org.java.financial.exception.UserAlreadyExistsException;
import org.java.financial.exception.UserNotFoundException;
import org.java.financial.exception.RoleNotFoundException;

public interface UserService {

    /**
     * ✅ Registers a new user.
     */
    UserEntity registerUser(String username, String password, String role) throws RoleNotFoundException, UserAlreadyExistsException;

    /**
     * ✅ Fetches an existing user by username.
     */
    UserEntity findUserByUsername(String username) throws UserNotFoundException;

    /**
     * ✅ Checks if a username already exists.
     */
    boolean userExists(String username);
}
