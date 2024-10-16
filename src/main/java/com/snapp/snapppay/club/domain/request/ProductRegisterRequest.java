package com.snapp.snapppay.club.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class ProductRegisterRequest {

    @NotBlank
    private String title;
    private String description;
    @URL
    private String image;
    @NotNull
    @Positive
    private Integer scorePrice;

}
