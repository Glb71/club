package com.snapp.snapppay.club.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "request to register user")
public class UserRegisterRequest {

    @NotBlank(message = "username is mandatory")
    @Schema(description = "requested username")
    private String username;
    @NotBlank(message = "password is mandatory")
    private String password;
    @NotBlank(message = "national code is mandatory")
    @Size(min = 10, max = 10, message = "national code is not valid")
    @Schema(minLength = 10, maxLength = 10)
    private String nationalCode;

}
