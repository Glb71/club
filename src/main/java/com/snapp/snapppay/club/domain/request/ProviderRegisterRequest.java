package com.snapp.snapppay.club.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProviderRegisterRequest {

    @NotBlank(message = "title is mandatory")
    private String title;
    private String url;
    @NotNull(message = "user id is mandatory")
    private Long userId;

}
