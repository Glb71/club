package com.snapp.snapppay.club.service;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.request.PageRequest;
import com.snapp.snapppay.club.domain.response.UserResponse;
import com.snapp.snapppay.club.mapper.UserResponseMapper;
import com.snapp.snapppay.club.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserResponseMapper userResponseMapper;

    @Override
    public Page<UserResponse> search(String search, PageRequest pageRequest) {
        Page<User> users = userRepository.searchAllByUsername(search, pageRequest.pageable());
        return users.map(userResponseMapper::map);
    }
}
