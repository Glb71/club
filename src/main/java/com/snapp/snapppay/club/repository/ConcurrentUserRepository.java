package com.snapp.snapppay.club.repository;

import com.snapp.snapppay.club.domain.entity.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ConcurrentUserRepository extends JpaRepository<User, Long> {

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<User> findById(Long id);
}
