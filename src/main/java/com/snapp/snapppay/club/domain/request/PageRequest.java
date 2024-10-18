package com.snapp.snapppay.club.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@Schema(description = "page request in searches")
public class PageRequest {

    @Schema(description = "page number", minimum = "0")
    @NotNull(message = "page is mandatory")
    private Integer page;
    @Schema(description = "page size", minimum = "1", defaultValue = "20")
    private Integer size;

    public Pageable pageable() {
        return org.springframework.data.domain.PageRequest.of(page, size != null ? size : 20);
    }

}
