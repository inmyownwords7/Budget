package org.java.financial.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import java.util.Objects;

/**
 * Represents a role assigned to a user in the system.
 * <p>
 * Implements {@link GrantedAuthority} to integrate with Spring Security's authentication system.
 * </p>
 */
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    /**
     * Unique identifier for the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the role (e.g., "ROLE_ADMIN", "ROLE_USER").
     * <p>
     * This field is unique and cannot be null.
     * </p>
     */
    @Column(unique = true, nullable = false)
    private String roleName;

    /**
     * Default constructor required by JPA.
     * <p>
     * Protected to prevent direct instantiation outside of JPA.
     * </p>
     */
    protected Role() {}

    /**
     * Constructs a new Role with the specified name.
     *
     * @param roleName The name of the role.
     */
    public Role(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Returns the unique ID of the role.
     *
     * @return The role ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the name of the role.
     *
     * @return The role name.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Sets the name of the role.
     *
     * @param roleName The new role name.
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Returns the role name as the authority string for Spring Security.
     * <p>
     * This is required by the {@link GrantedAuthority} interface.
     * </p>
     *
     * @return The role name, which represents the granted authority.
     */
    @Override
    public String getAuthority() {
        return roleName;
    }

    /**
     * Determines equality between two Role objects based on their role name.
     *
     * @param o The object to compare.
     * @return {@code true} if the role names are equal, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(roleName, role.roleName);
    }

    /**
     * Computes a hash code based on the role name.
     *
     * @return The hash code of the role.
     */
    @Override
    public int hashCode() {
        return Objects.hash(roleName);
    }

    /**
     * Returns a string representation of the role.
     *
     * @return A formatted string containing role details.
     */
    @Override
    public String toString() {
        return "Role{id=" + id + ", roleName='" + roleName + "'}";
    }
}
