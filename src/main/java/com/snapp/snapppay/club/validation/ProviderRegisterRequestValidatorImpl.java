package com.snapp.snapppay.club.validation;

import com.snapp.snapppay.club.domain.request.ProviderRegisterRequest;
import com.snapp.snapppay.club.exception.ExceptionMessageCode;
import com.snapp.snapppay.club.exception.ValidationException;
import com.snapp.snapppay.club.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProviderRegisterRequestValidatorImpl implements ProviderRegisterRequestValidator {

    private final ProviderRepository providerRepository;

    @Override
    public void validate(ProviderRegisterRequest providerRegisterRequest) {
        validateTitle(providerRegisterRequest);
        validateUser(providerRegisterRequest);
    }

    private void validateTitle(ProviderRegisterRequest providerRegisterRequest) {
        if (providerRepository.existsByTitle(providerRegisterRequest.getTitle())) {
            throw new ValidationException(ExceptionMessageCode.PROVIDER_TITLE_IS_DUPLICATE);
        }
    }

    private void validateUser(ProviderRegisterRequest providerRegisterRequest) {
        if (providerRepository.existsByUser_Id(providerRegisterRequest.getUserId())) {
            throw new ValidationException(ExceptionMessageCode.THIS_USER_IS_ALREADY_A_PROVIDER);
        }
    }
}
