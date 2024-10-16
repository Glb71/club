package com.snapp.snapppay.club.service.product;

import com.snapp.snapppay.club.domain.entity.Product;
import com.snapp.snapppay.club.domain.request.PageRequest;
import com.snapp.snapppay.club.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchAll(String search, PageRequest pageRequest) {
        return productRepository.searchAll(search, pageRequest.pageable());
    }
}
