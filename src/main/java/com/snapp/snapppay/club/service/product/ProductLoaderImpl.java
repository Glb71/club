package com.snapp.snapppay.club.service.product;

import com.snapp.snapppay.club.domain.entity.Product;
import com.snapp.snapppay.club.exception.DataNotFoundException;
import com.snapp.snapppay.club.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductLoaderImpl implements ProductLoader {

    private final ProductRepository productRepository;

    @Override
    public Product loadActive(Long id) {
        return productRepository.findByIdAndActiveTrue(id).
                orElseThrow(() -> new DataNotFoundException(Product.class, id));
    }
}
