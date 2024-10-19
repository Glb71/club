package com.snapp.snapppay.club.service.purchase;

import com.snapp.snapppay.club.domain.entity.Product;
import com.snapp.snapppay.club.domain.entity.Purchase;
import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.repository.PurchaseRepository;
import com.snapp.snapppay.club.service.product.ProductLoader;
import com.snapp.snapppay.club.service.user.UserLoader;
import com.snapp.snapppay.club.service.userScore.UserScoreUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final UserLoader userLoader;
    private final ProductLoader productLoader;
    private final UserScoreUpdater userScoreUpdater;
    private final PurchaseRepository purchaseRepository;

    @Override
    @Transactional
    public void purchase(Long productId) {
        Purchase purchase = initPurchase(productId);
        userScoreUpdater.reduce(purchase);
        purchaseRepository.save(purchase);
    }

    private Purchase initPurchase(Long productId) {
        User currentUser = userLoader.current();
        Product product = productLoader.loadActive(productId);
        return Purchase.builder()
                .product(product)
                .user(currentUser)
                .usedScore(product.getScorePrice())
                .build();
    }
}
