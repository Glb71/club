package com.snapp.snapppay.club.repository;

import com.snapp.snapppay.club.domain.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByProvider_idAndUser_Id(Long providerId, Long userId);

}
