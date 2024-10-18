package com.snapp.snapppay.club.service.user;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserRole;
import com.snapp.snapppay.club.enums.Roles;
import com.snapp.snapppay.club.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRepository userRepository;
    private final UserLoader userLoader;

    @Override
    public void makeProvider(Long userId) {
        addRole(userId, Roles.PROVIDER);
    }

    private void addRole(Long userId, Roles role) {
        User user = userLoader.load(userId);
        if (user.getRoles().stream().noneMatch(userRole -> userRole.getRole().equals(role))) {
            user.getRoles().add(new UserRole(role));
        }
        userRepository.save(user);
    }
}
