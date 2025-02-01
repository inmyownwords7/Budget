package org.java.financial.entity;

import org.springframework.security.core.GrantedAuthority;
import jakarta.persistence.*;
import java.util.Set;
import org.java.financial.entity.User;
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ Auto-increment role_id
    private Long roleId;

    @Column(nullable = false, unique = true) // ✅ Role names are unique
    private String roleName;

    @ManyToMany(mappedBy = "roles") // ✅ Users can share roles
    private Set<User> users;

    public Role() {}

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    // ✅ Required by GrantedAuthority interface
    @Override
    public String getAuthority() {
        return roleName;
    }
}
