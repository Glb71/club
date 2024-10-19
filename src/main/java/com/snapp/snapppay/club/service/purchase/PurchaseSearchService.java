package com.snapp.snapppay.club.service.purchase;

import com.snapp.snapppay.club.domain.entity.Purchase;
import com.snapp.snapppay.club.domain.request.PageRequest;
import org.springframework.data.domain.Page;

public interface PurchaseSearchService {

    Page<Purchase> search(String search, PageRequest pageRequest);

}
