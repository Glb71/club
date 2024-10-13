package com.snapp.snapppay.club.service;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserRole;
import com.snapp.snapppay.club.domain.request.UserRegisterRequest;
import com.snapp.snapppay.club.enums.Roles;
import com.snapp.snapppay.club.repository.UserRepository;
import com.snapp.snapppay.club.validation.UserRegisterRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserRegisterServiceImpl implements UserRegisterService {

    private final UserRegisterRequestValidator userRegisterRequestValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void register(UserRegisterRequest userRegisterRequest) {
        userRegisterRequestValidator.validate(userRegisterRequest);
        User user = new User();
        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        user.setNationalCode(userRegisterRequest.getNationalCode());
        user.setRoles(initDefaultRoles());
        userRepository.save(user);
    }

    private Set<UserRole> initDefaultRoles() {
        UserRole userRole = new UserRole();
        userRole.setRole(Roles.getDefaultRole());
        return Collections.singleton(userRole);
    }

}
