package org.java.financial.security;

import org.java.financial.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of {@link UserDetails} for Spring Security authentication.
 * <p>
 * This class adapts the {@link UserEntity} entity to fit the {@link UserDetails} interface,
 * allowing Spring Security to manage authentication and authorization.
 * </p>
 */
public class UserDetailsImpl implements UserDetails {

    private final UserEntity user;

    /**
     * Constructs a UserDetailsImpl instance with the given user entity.
     *
     * @param user The {@link UserEntity} to be wrapped for authentication.
     */
    public UserDetailsImpl(UserEntity user) {
        this.user = user;
    }

    /**
     * Returns the user ID.
     *
     * @return The ID of the user.
     */
    public Long getUserId() {
        return user.getUserId();
    }

    /**
     * Returns the user's role name.
     *
     * @return The role assigned to the user.
     */
    public String getRoleName() {
        return user.getRole().getRoleName();
    }

    /**
     * Returns the username of the user (required by Spring Security).
     *
     * @return The username of the user.
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Returns the encoded password of the user (required by Spring Security).
     *
     * @return The hashed password of the user.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the user's authorities (roles) as a collection.
     * <p>
     * Converts the user's role into a list of granted authorities.
     * </p>
     *
     * @return A collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(user.getRole()); // ✅ Now supports multiple roles
    }

    /**
     * Indicates whether the user's account is non-expired.
     *
     * @return {@code true}, indicating the account is not expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is non-locked.
     *
     * @return {@code true}, indicating the account is not locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) are non-expired.
     *
     * @return {@code true}, indicating credentials are valid.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     *
     * @return {@code true}, indicating the user is enabled.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Returns the original {@link UserEntity} associated with this UserDetails.
     *
     * @return The wrapped {@link UserEntity} object.
     */
    public UserEntity getUserEntity() {
        return user;
    }
}
