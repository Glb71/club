package com.snapp.snapppay.club.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "product response")
public class UserProductResponse {

    private Long id;
    private String title;
    private String description;
    @Schema(description = "product image url")
    private String image;
    private Integer scoreAmount;
    private Long version;

}
