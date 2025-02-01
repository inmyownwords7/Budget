package org.java.financial.dto;

/**
 * A record that represents user data without exposing sensitive information.
 *
 * @param username The username of the user.
 * @param role The user's role name.
 */
public record UserResponseDTO(String username, String role) {
}
