package com.snapp.snapppay.club.api;

import com.snapp.snapppay.club.domain.entity.Product;
import com.snapp.snapppay.club.domain.request.PageRequest;
import com.snapp.snapppay.club.domain.request.ProductRegisterRequest;
import com.snapp.snapppay.club.domain.response.AdminProductResponse;
import com.snapp.snapppay.club.domain.response.UserProductResponse;
import com.snapp.snapppay.club.mapper.AdminProductResponseMapper;
import com.snapp.snapppay.club.mapper.UserProductResponseMapper;
import com.snapp.snapppay.club.service.product.ProductRegisterService;
import com.snapp.snapppay.club.service.product.ProductSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Tag(
        name = "Product Api",
        description = "add , search all for admin , search actives for clients"
)
public class ProductApi {

    private final ProductRegisterService productRegisterService;
    private final ProductSearchService productSearchService;
    private final AdminProductResponseMapper adminProductResponseMapper;
    private final UserProductResponseMapper userProductResponseMapper;

    @Operation(
            summary = "add new product",
            description = "creates a new product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "ProductRegisterRequest for creating product from it",
                    content = @Content(schema = @Schema(implementation = ProductRegisterRequest.class))
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "product created successfully",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "401", description = "expired token",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "403", description = "Access Denied",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "400", description = "invalid input (missing required fields in body or validations)",
                            content = {@Content(mediaType = "application/json")})
            }
    )
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> add(@Valid @RequestBody ProductRegisterRequest productRegisterRequest) {
        productRegisterService.register(productRegisterRequest);
        return ResponseEntity.ok("success");
    }

    @Operation(
            summary = "search all products",
            description = "for admin"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "search successful",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "401", description = "expired token",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "403", description = "Access Denied",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "400", description = "invalid input (missing page parameter)",
                            content = {@Content(mediaType = "application/json")})
            }
    )
    @GetMapping("/search/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<AdminProductResponse>> searchAll(@Parameter(description = "search parameter (title or description filter)")
                                                                @RequestParam(name = "search", required = false) String search,
                                                                @Valid PageRequest pageRequest) {
        Page<Product> products = productSearchService.searchAll(search, pageRequest);
        return ResponseEntity.ok(products.map(adminProductResponseMapper::map));
    }

    @Operation(
            summary = "search active products",
            description = "for users"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "search successful",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "401", description = "expired token",
                            content = {@Content(mediaType = "text/plain")}),
                    @ApiResponse(responseCode = "400", description = "invalid input (missing page parameter)",
                            content = {@Content(mediaType = "application/json")})
            }
    )
    @GetMapping("/search/actives")
    public ResponseEntity<Page<UserProductResponse>> searchActives(@RequestParam(name = "search", required = false) String search,
                                                                   @Valid PageRequest pageRequest) {
        Page<Product> products = productSearchService.searchActives(search, pageRequest);
        return ResponseEntity.ok(products.map(userProductResponseMapper::map));
    }

}
