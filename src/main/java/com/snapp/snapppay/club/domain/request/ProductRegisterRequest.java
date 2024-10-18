package com.snapp.snapppay.club.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@Schema(description = "request to register new product")
public class ProductRegisterRequest {

    @NotBlank
    @Schema(description = "product title (must be unique)")
    private String title;
    @Schema(description = "product description")
    private String description;
    @URL
    @Schema(description = "product image url")
    private String image;
    @NotNull
    @Positive
    @Schema(description = "product price in score amount", minimum = "1")
    private Integer scorePrice;

}
