package com.snapp.snapppay.club.service.product;

import com.snapp.snapppay.club.domain.entity.Product;
import com.snapp.snapppay.club.domain.request.ProductRegisterRequest;
import com.snapp.snapppay.club.mapper.ProductMapper;
import com.snapp.snapppay.club.repository.ProductRepository;
import com.snapp.snapppay.club.validation.ProductRegisterRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductRegisterServiceImpl implements ProductRegisterService {

    private final ProductMapper productMapper;
    private final ProductRegisterRequestValidator productRegisterRequestValidator;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void register(ProductRegisterRequest productRegisterRequest) {
        productRegisterRequestValidator.validate(productRegisterRequest);
        Product product = productMapper.map(productRegisterRequest);
        productRepository.save(product);
    }
}
