package org.java.financial.service;

import org.java.financial.entity.UserEntity;

import javax.management.relation.RoleNotFoundException;
//UserService should handle user registration, role management, and fetching user data.
public interface UserService {

    /**
     * ✅ Register a new user.
     */
    UserEntity registerUser(String username, String password, String role) throws RoleNotFoundException;

    /**
     * ✅ Check if user exists.
     */
    boolean userExists(String username);

//    boolean validateUserCredentials(String username, String password);
}
