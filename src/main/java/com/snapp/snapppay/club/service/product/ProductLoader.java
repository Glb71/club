package com.snapp.snapppay.club.service.product;

import com.snapp.snapppay.club.domain.entity.Product;

public interface ProductLoader {

    Product loadActive(Long id);

}
