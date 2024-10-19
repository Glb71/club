package com.snapp.snapppay.club.repository;

import com.snapp.snapppay.club.domain.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("select o from purchase o where o.user.id = :userId and (:search is null or o.product.title like %:search%)")
    Page<Purchase> search(@Param("userId") Long userId,@Param("search") String search, Pageable pageable);

}
