package com.snapp.snapppay.club.api;

import com.snapp.snapppay.club.domain.entity.Product;
import com.snapp.snapppay.club.domain.request.ProductRegisterRequest;
import com.snapp.snapppay.club.exception.ExceptionMessageCode;
import com.snapp.snapppay.club.repository.ProductRepository;
import com.snapp.snapppay.club.test.BaseIntegrationTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration tests for Product API")
@Tag("integration")
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductApiIT extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;

    private String userToken;
    private String adminToken;

    @BeforeEach
    void setup() {
        insertDefaultUser();
        insertDefaultAdmin();

        userToken = getDefaultUserToken();
        adminToken = getDefaultAdminToken();

        Product product = new Product();
        product.setTitle("testProduct");
        product.setScorePrice(100);
        productRepository.save(product);

        Product notActiveProduct = new Product();
        notActiveProduct.setTitle("notActiveProduct");
        notActiveProduct.setScorePrice(200);
        notActiveProduct.setActive(false);
        productRepository.save(notActiveProduct);
    }

    @Test
    @SneakyThrows
    void testAddSuccess() {
        ProductRegisterRequest productRegisterRequest = new ProductRegisterRequest();
        productRegisterRequest.setTitle("product1");
        productRegisterRequest.setScorePrice(200);
        mockMvc.perform(addProductRequest(productRegisterRequest, adminToken)).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testDuplicateProductTitleError() {
        ProductRegisterRequest productRegisterRequest = new ProductRegisterRequest();
        productRegisterRequest.setTitle("testProduct");
        productRegisterRequest.setScorePrice(200);
        mockMvc.perform(addProductRequest(productRegisterRequest, adminToken)).andExpect(status().isBadRequest())
                .andExpect(content().string(ExceptionMessageCode.PRODUCT_TITLE_IS_DUPLICATE));
    }

    @Test
    @SneakyThrows
    void testAddProductAccess() {
        ProductRegisterRequest productRegisterRequest = new ProductRegisterRequest();
        productRegisterRequest.setTitle("product1");
        productRegisterRequest.setScorePrice(200);
        mockMvc.perform(addProductRequest(productRegisterRequest, userToken)).andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void testSearchAllProduct() {
        mockMvc.perform(buildGetRequest("/api/product/search/all", adminToken).param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(2)));
    }

    @Test
    @SneakyThrows
    void testSearchAllProductAccess() {
        mockMvc.perform(
                        buildGetRequest("/api/product/search/all", userToken).param("page", "0")).
                andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void testSearchActiveProduct() {
        mockMvc.perform(buildGetRequest("/api/product/search/actives", adminToken).
                        param("page", "0")).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(1)));
    }

    @SneakyThrows
    private MockHttpServletRequestBuilder addProductRequest(ProductRegisterRequest body, String token) {
        return buildPostRequest("/api/product/add", body, token);
    }

}
