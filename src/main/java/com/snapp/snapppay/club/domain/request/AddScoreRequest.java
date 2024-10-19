package com.snapp.snapppay.club.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "request to add score")
@Builder
public class AddScoreRequest {
    @Schema(description = "score will be added to this username")
    @NotBlank
    private String username;
    @NotNull
    @Positive
    @Schema(description = "this amount of score will be added", minimum = "1")
    private Integer scoreAmount;
    @NotBlank
    @Schema(description = "cause of acquiring score")
    private String cause;
}
