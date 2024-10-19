package com.snapp.snapppay.club.repository;

import com.snapp.snapppay.club.domain.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
