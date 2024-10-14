package com.snapp.snapppay.club.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class PageRequest {

    @NotNull(message = "page is mandatory")
    private Integer page;
    private Integer size;

    public Pageable pageable() {
        return org.springframework.data.domain.PageRequest.of(page, size != null ? size : 20);
    }

}
