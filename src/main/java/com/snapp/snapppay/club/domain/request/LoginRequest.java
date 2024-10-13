package com.snapp.snapppay.club.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "username is mandatory")
    private String username;
    @NotBlank(message = "password is mandatory")
    private String password;

}
