package com.snapp.snapppay.club.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "request to register new provider")
public class ProviderRegisterRequest {

    @Schema(description = "provider title (unique)")
    @NotBlank(message = "title is mandatory")
    private String title;
    @Schema(description = "provider home page url")
    private String url;
    @NotNull(message = "user id is mandatory")
    @Schema(description = "provider user id (unique)")
    private Long userId;

}
