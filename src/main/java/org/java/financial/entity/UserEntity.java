package org.java.financial.entity;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Represents a user in the financial application.
 * <p>
 * This entity is mapped to the "users" table in the database and includes authentication details.
 * </p>
 */
@Entity
@Table(name = "users")
public class UserEntity {

    /**
     * Unique identifier for the user.
     * <p>
     * This field is auto-generated using an identity strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * The unique username of the user.
     * <p>
     * This field cannot be null and must be unique across all users.
     * </p>
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * The securely hashed password of the user.
     */
    @Column(nullable = false)
    private String password;

    /**
     * The role assigned to the user, defining their permissions.
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    /**
     * Default constructor required by JPA.
     */
    public UserEntity() {
    }

    /**
     * Constructs a new UserEntity with the specified details.
     *
     * @param username The unique username.
     * @param password The hashed password.
     * @param role     The role assigned to the user.
     */
    public UserEntity(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Returns the user ID.
     *
     * @return The unique identifier of the user.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Returns the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Updates the username of the user.
     *
     * @param username The new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the hashed password of the user.
     *
     * @return The hashed password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Updates the user's password.
     *
     * @param password The new hashed password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the role assigned to the user.
     *
     * @return The role.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Updates the user's role.
     *
     * @param role The new role.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Determines equality between two UserEntity objects based on their username.
     *
     * @param o The object to compare.
     * @return {@code true} if the usernames are equal, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(username, that.username);
    }

    /**
     * Computes a hash code based on the username.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    /**
     * Returns a string representation of the user.
     *
     * @return A formatted string containing user details.
     */
    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", role=" + role.getRoleName() +
                '}';
    }
}
