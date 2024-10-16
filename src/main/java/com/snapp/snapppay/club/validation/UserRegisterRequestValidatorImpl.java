package com.snapp.snapppay.club.validation;

import com.snapp.snapppay.club.domain.request.UserRegisterRequest;
import com.snapp.snapppay.club.exception.ExceptionMessageCode;
import com.snapp.snapppay.club.exception.ValidationException;
import com.snapp.snapppay.club.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisterRequestValidatorImpl implements UserRegisterRequestValidator {

    private final UserRepository userRepository;

    @Override
    public void validate(UserRegisterRequest userRegisterRequest) {
        validateUsername(userRegisterRequest);
        validateNationalCode(userRegisterRequest);
    }

    private void validateUsername(UserRegisterRequest userRegisterRequest) {
        if (userRepository.existsByUsername(userRegisterRequest.getUsername())) {
            throw new ValidationException(ExceptionMessageCode.USERNAME_IS_DUPLICATE);
        }
    }

    private void validateNationalCode(UserRegisterRequest userRegisterRequest) {
        if (userRepository.existsByNationalCode(userRegisterRequest.getNationalCode())) {
            throw new ValidationException(ExceptionMessageCode.NATIONAL_CODE_IS_DUPLICATE);
        }
    }
}
