package com.snapp.snapppay.club.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequest {

    @NotBlank(message = "username is mandatory")
    private String username;
    @NotBlank(message = "password is mandatory")
    private String password;
    @NotBlank(message = "national code is mandatory")
    @Size(min = 10, max = 10, message = "national code is not valid")
    private String nationalCode;

}
