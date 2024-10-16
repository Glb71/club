package com.snapp.snapppay.club.validation;

import com.snapp.snapppay.club.domain.request.ProductRegisterRequest;
import com.snapp.snapppay.club.exception.ExceptionMessageCode;
import com.snapp.snapppay.club.exception.ValidationException;
import com.snapp.snapppay.club.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRegisterRequestValidatorImpl implements ProductRegisterRequestValidator {

    private final ProductRepository productRepository;

    @Override
    public void validate(ProductRegisterRequest productRegisterRequest) {
        validateTitle(productRegisterRequest);
    }

    private void validateTitle(ProductRegisterRequest productRegisterRequest) {
        if (productRepository.existsByTitle(productRegisterRequest.getTitle())) {
            throw new ValidationException(ExceptionMessageCode.PRODUCT_TITLE_IS_DUPLICATE);
        }
    }
}
