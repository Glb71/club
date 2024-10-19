package com.snapp.snapppay.club.api;

import com.snapp.snapppay.club.domain.entity.Product;
import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserScore;
import com.snapp.snapppay.club.domain.request.ProductRegisterRequest;
import com.snapp.snapppay.club.exception.ExceptionMessageCode;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    private String userToken;
    private String adminToken;
    private User defaultUser;
    private Product testProduct;

    @BeforeEach
    void setup() {
        defaultUser = insertDefaultUser();
        addScoreToUser(defaultUser, 220);
        insertDefaultAdmin();

        userToken = getDefaultUserToken();
        adminToken = getDefaultAdminToken();

        testProduct = insertProduct("testProduct", 100, true);

        insertProduct("notActiveProduct", 200, false);
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

    @Test
    @SneakyThrows
    void testPurchaseSuccess() {
        mockMvc.perform(buildPostRequest("/api/product/purchase/" + testProduct.getId(), userToken)).andExpect(status().isOk());
        Optional<UserScore> userScore = userScoreRepository.findByUser_Id(defaultUser.getId());
        assertTrue(userScore.isPresent());
        assertEquals(120, userScore.get().getCurrentScore());
        assertEquals(100, userScore.get().getUsedScore());
    }

    @Test
    @SneakyThrows
    void testPurchaseConcurrency() {
        Product product = insertProduct("product 1 score", 1, true);
        List<Thread> threads = new ArrayList<>();
        int counter = 0;
        while (counter < 100) {
            Thread thread = new Thread(() -> {
                try {
                    mockMvc.perform(buildPostRequest("/api/product/purchase/" + product.getId(), userToken)).andExpect(status().isOk());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            threads.add(thread);
            thread.start();
            counter++;
        }
        for (Thread thread : threads) {
            thread.join();
        }

        Optional<UserScore> userScore = userScoreRepository.findByUser_Id(defaultUser.getId());
        assertTrue(userScore.isPresent());
        assertEquals(120, userScore.get().getCurrentScore());
        assertEquals(100, userScore.get().getUsedScore());
    }

    @Test
    @SneakyThrows
    void testPurchaseInsufficientScore() {
        Product product = insertProduct("product 1000 score", 1000, true);
        mockMvc.perform(buildPostRequest("/api/product/purchase/" + product.getId(), userToken)).andExpect(status().isBadRequest())
                .andExpect(content().string(ExceptionMessageCode.INSUFFICIENT_SCORE));
    }

    @SneakyThrows
    private MockHttpServletRequestBuilder addProductRequest(ProductRegisterRequest body, String token) {
        return buildPostRequest("/api/product/add", body, token);
    }

}
