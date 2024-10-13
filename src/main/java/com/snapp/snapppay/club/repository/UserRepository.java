package com.snapp.snapppay.club.repository;

import com.snapp.snapppay.club.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByNationalCode(String nationalCode);

}
