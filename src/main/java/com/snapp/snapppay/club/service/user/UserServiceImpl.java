package com.snapp.snapppay.club.service.user;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.request.PageRequest;
import com.snapp.snapppay.club.domain.response.UserResponse;
import com.snapp.snapppay.club.mapper.UserResponseMapper;
import com.snapp.snapppay.club.repository.UserRepository;
import com.snapp.snapppay.club.security.UserPrincipal;
import com.snapp.snapppay.club.security.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserResponseMapper userResponseMapper;
    private final TokenService tokenService;

    @Override
    public Page<UserResponse> search(String search, PageRequest pageRequest) {
        Page<User> users = userRepository.searchAllByUsername(search, pageRequest.pageable());
        return users.map(userResponseMapper::map);
    }

    @Override
    public User getCurrentUser() {
        UserPrincipal userPrincipal = tokenService.getCurrentUserPrincipal();
        return userRepository.findById(userPrincipal.getId()).orElse(null);
    }
}
