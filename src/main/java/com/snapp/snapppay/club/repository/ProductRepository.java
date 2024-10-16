package com.snapp.snapppay.club.repository;

import com.snapp.snapppay.club.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByTitle(String title);

}
