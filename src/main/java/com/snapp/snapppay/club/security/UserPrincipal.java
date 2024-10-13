package com.snapp.snapppay.club.security;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

@Getter
public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final boolean active;
    private final Set<GrantedAuthority> authorities = new HashSet<>();

    protected UserPrincipal(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.active = user.getActive();
        for (UserRole role : user.getRoles()) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getRole().name());
            this.authorities.add(simpleGrantedAuthority);
        }
    }
}
