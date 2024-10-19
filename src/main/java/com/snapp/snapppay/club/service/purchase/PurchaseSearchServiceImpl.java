package com.snapp.snapppay.club.service.purchase;

import com.snapp.snapppay.club.domain.entity.Purchase;
import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.request.PageRequest;
import com.snapp.snapppay.club.repository.PurchaseRepository;
import com.snapp.snapppay.club.service.user.UserLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseSearchServiceImpl implements PurchaseSearchService {

    private final PurchaseRepository purchaseRepository;
    private final UserLoader userLoader;

    @Override
    public Page<Purchase> search(String search, PageRequest pageRequest) {
        User current = userLoader.current();
        return purchaseRepository.search(current.getId(), search, pageRequest.pageable());
    }
}
