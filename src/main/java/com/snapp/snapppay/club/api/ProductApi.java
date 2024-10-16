package com.snapp.snapppay.club.api;

import com.snapp.snapppay.club.domain.request.ProductRegisterRequest;
import com.snapp.snapppay.club.service.product.ProductRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductApi {

    private final ProductRegisterService productRegisterService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String add(@Valid @RequestBody ProductRegisterRequest productRegisterRequest) {
        productRegisterService.register(productRegisterRequest);
        return "success";
    }

}
