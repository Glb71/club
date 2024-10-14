package com.snapp.snapppay.club.service.user;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.exception.DataNotFoundException;
import com.snapp.snapppay.club.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoaderServiceImpl implements UserLoaderService {

    private final UserRepository userRepository;

    @Override
    public User load(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException(User.class, id));
    }
}
