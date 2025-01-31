package org.java.financial.security;

import org.java.financial.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * UserDetailsImpl implements Spring Security's UserDetails interface.
 * It allows Spring Security to manage authentication and authorization using the User entity.
 */
public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    /**
     * Returns the user ID.
     */
    public Long getUserId() {
        return user.getUserId();
    }

    /**
     * Returns the user's role name.
     */
    public String getRoleName() {
        return user.getRole().getRoleName();
    }

    /**
     * Returns the username (required by Spring Security).
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Returns the encoded password (required by Spring Security).
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the user's role as a collection of authorities (required by Spring Security).
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(user.getRole()); // Role implements GrantedAuthority
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Returns the original User entity.
     */
    public User getUser() {
        return user;
    }
}
