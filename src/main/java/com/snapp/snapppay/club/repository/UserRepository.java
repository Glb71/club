package com.snapp.snapppay.club.repository;

import com.snapp.snapppay.club.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByNationalCode(String nationalCode);

    @Query("select o from user o where :search is null or o.username like %:search%")
    Page<User> searchAllByUsername(@Param("search") String search, Pageable pageable);

}
