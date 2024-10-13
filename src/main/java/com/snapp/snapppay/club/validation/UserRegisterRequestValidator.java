package com.snapp.snapppay.club.validation;

import com.snapp.snapppay.club.domain.request.UserRegisterRequest;

public interface UserRegisterRequestValidator {

    void validate(UserRegisterRequest userRegisterRequest);

}
