package com.snapp.snapppay.club.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Schema(name = "product response")
public class AdminProductResponse {

    private Long id;
    private String title;
    private String description;
    @Schema(description = "product image url")
    private String image;
    private Integer scoreAmount;
    @Schema(description = "product creator username")
    private String creator;
    @Schema(description = "product updater username")
    private String updater;
    private Timestamp creationTime;
    private Timestamp lastUpdateTime;
    private Long version;

}
