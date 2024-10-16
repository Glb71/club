package com.snapp.snapppay.club.repository;

import com.snapp.snapppay.club.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select o from user o join fetch o.roles r where o.username = :username")
    Optional<User> findByUsernameEager(@Param("username") String username);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByNationalCode(String nationalCode);

    @Query("select o from user o where :search is null or o.username like %:search%")
    Page<User> searchAllByUsername(@Param("search") String search, Pageable pageable);

}
