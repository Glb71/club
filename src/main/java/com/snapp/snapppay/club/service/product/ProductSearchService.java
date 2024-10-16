package com.snapp.snapppay.club.service.product;

import com.snapp.snapppay.club.domain.entity.Product;
import com.snapp.snapppay.club.domain.request.PageRequest;
import org.springframework.data.domain.Page;

public interface ProductSearchService {

    Page<Product> searchAll(String search, PageRequest pageRequest);

}
