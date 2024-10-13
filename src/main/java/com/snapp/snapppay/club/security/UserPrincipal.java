package com.snapp.snapppay.club.security;

import com.snapp.snapppay.club.entity.Role;
import com.snapp.snapppay.club.entity.User;
import com.snapp.snapppay.club.entity.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final boolean active;
    private final Set<GrantedAuthority> authorities;

    protected UserPrincipal(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.active = user.getActive();
        this.authorities = user.getRoles().stream().map(UserRole::getRole).
                map(Role::getRoleCode).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }
}
