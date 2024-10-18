package com.snapp.snapppay.club.test;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserRole;
import com.snapp.snapppay.club.enums.Roles;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@RequiredArgsConstructor
public class UserTestObjectContainer {
    private final PasswordEncoder passwordEncoder;

    public static final String DEFAULT_USERNAME = "user";
    public static final String DEFAULT_USER_PASSWORD = "userTestPassword";
    public static final String DEFAULT_USER_NATIONAL_CODE = "1111111111";

    public static final String DEFAULT_ADMIN_USERNAME = "admin";
    public static final String DEFAULT_ADMIN_PASSWORD = "adminPassword";
    public static final String DEFAULT_ADMIN_NATIONAL_CODE = "1111111112";
    public static final String INVALID_USER_PASSWORD = "invalidPassword";

    @Getter
    User defaultUser;

    @Getter
    User defaultAdmin;

    User createDefaultUser() {
        defaultUser = User.builder()
                .username(DEFAULT_USERNAME)
                .password(passwordEncoder.encode(DEFAULT_USER_PASSWORD))
                .nationalCode(DEFAULT_USER_NATIONAL_CODE)
                .roles(Collections.singleton(new UserRole(Roles.USER)))
                .build();
        return defaultUser;
    }

    User createDefaultAdmin() {

        defaultAdmin = User.builder()
                .username(DEFAULT_ADMIN_USERNAME)
                .password(passwordEncoder.encode(DEFAULT_ADMIN_PASSWORD))
                .nationalCode(DEFAULT_ADMIN_NATIONAL_CODE)
                .roles(Collections.singleton(new UserRole(Roles.ADMIN)))
                .build();
        return defaultAdmin;
    }

}
