package com.snapp.snapppay.club.repository;

import com.snapp.snapppay.club.domain.entity.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserScoreRepository extends JpaRepository<UserScore, Long> {

    Optional<UserScore> findByUser_Id(Long userId);

}
