package com.snapp.snapppay.club.api;

import com.snapp.snapppay.club.domain.entity.Product;
import com.snapp.snapppay.club.domain.request.PageRequest;
import com.snapp.snapppay.club.domain.request.ProductRegisterRequest;
import com.snapp.snapppay.club.domain.response.AdminProductResponse;
import com.snapp.snapppay.club.mapper.AdminProductResponseMapper;
import com.snapp.snapppay.club.service.product.ProductRegisterService;
import com.snapp.snapppay.club.service.product.ProductSearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductApi {

    private final ProductRegisterService productRegisterService;
    private final ProductSearchService productSearchService;
    private final AdminProductResponseMapper adminProductResponseMapper;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String add(@Valid @RequestBody ProductRegisterRequest productRegisterRequest) {
        productRegisterService.register(productRegisterRequest);
        return "success";
    }

    @GetMapping("/search/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<AdminProductResponse> searchAll(@RequestParam(name = "search", required = false) String search,
                                                @Valid PageRequest pageRequest) {
        Page<Product> products = productSearchService.searchAll(search, pageRequest);
        return products.map(adminProductResponseMapper::map);
    }

}
