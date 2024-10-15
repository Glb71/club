package com.snapp.snapppay.club.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddScoreRequest {

    @NotBlank
    private String username;
    @NotNull
    @Positive
    private Integer scoreAmount;
    @NotBlank
    private String cause;
}
