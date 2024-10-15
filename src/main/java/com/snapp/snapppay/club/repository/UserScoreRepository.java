package com.snapp.snapppay.club.repository;

import com.snapp.snapppay.club.domain.entity.UserScore;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface UserScoreRepository extends JpaRepository<UserScore, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<UserScore> findByUser_Id(Long userId);

}
