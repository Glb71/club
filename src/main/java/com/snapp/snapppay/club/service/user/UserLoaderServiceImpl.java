package com.snapp.snapppay.club.service.user;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.exception.DataNotFoundException;
import com.snapp.snapppay.club.repository.UserRepository;
import com.snapp.snapppay.club.security.UserPrincipal;
import com.snapp.snapppay.club.security.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoaderServiceImpl implements UserLoaderService {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    public User load(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException(User.class, id));
    }

    @Override
    public User current() {
        UserPrincipal userPrincipal = tokenService.getCurrentUserPrincipal();
        return load(userPrincipal.getId());
    }
}
