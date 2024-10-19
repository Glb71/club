package com.snapp.snapppay.club.service.user;

import com.snapp.snapppay.club.repository.ConcurrentUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLockerImpl implements UserLocker {

    private final ConcurrentUserRepository concurrentUserRepository;

    @Override
    public void lock(Long userId) {
        concurrentUserRepository.findById(userId);
    }
}
