package com.snapp.snapppay.club.repository;

import com.snapp.snapppay.club.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByTitle(String title);

    @Query("select o from product o where :search is null or o.title like %:search% or o.description like %:search%")
    Page<Product> searchAll(@Param("search") String search, Pageable pageable);
}
