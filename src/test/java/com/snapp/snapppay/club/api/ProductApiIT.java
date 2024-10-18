package com.snapp.snapppay.club.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapp.snapppay.club.domain.entity.Product;
import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserRole;
import com.snapp.snapppay.club.domain.request.LoginRequest;
import com.snapp.snapppay.club.domain.request.ProductRegisterRequest;
import com.snapp.snapppay.club.enums.Roles;
import com.snapp.snapppay.club.exception.ExceptionMessageCode;
import com.snapp.snapppay.club.repository.ProductRepository;
import com.snapp.snapppay.club.repository.UserRepository;
import com.snapp.snapppay.club.security.jwt.JwtConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration tests for Product API")
@Tag("integration")
@ActiveProfiles("test")
class ProductApiIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtConfig jwtConfig;

    private String userToken;
    private String adminToken;

    @BeforeEach
    void setup() {
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("userPassword"));
        user.setNationalCode("1111111111");
        UserRole userRole = new UserRole();
        userRole.setRole(Roles.USER);
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("adminPassword"));
        admin.setNationalCode("1111111112");
        UserRole adminRole = new UserRole();
        adminRole.setRole(Roles.ADMIN);
        admin.setRoles(Collections.singleton(adminRole));
        userRepository.save(admin);
        userToken = getToken("user", "userPassword");
        adminToken = getToken("admin", "adminPassword");

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
        mockMvc.perform(buildGetRequest("/api/product/search/all", 0, adminToken)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(2)));
    }

    @Test
    @SneakyThrows
    void testSearchAllProductAccess() {
        mockMvc.perform(buildGetRequest("/api/product/search/all", 0, userToken)).andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void testSearchActiveProduct() {
        mockMvc.perform(buildGetRequest("/api/product/search/actives", 0, adminToken)).andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", is(1)));
    }


    @SneakyThrows
    private String getToken(String username, String password) {
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        return mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).
                andReturn().getResponse().getContentAsString();
    }

    @SneakyThrows
    private MockHttpServletRequestBuilder addProductRequest(ProductRegisterRequest body, String token) {
        return buildPostRequest("/api/product/add", body, token);
    }

    @SneakyThrows
    private MockHttpServletRequestBuilder buildPostRequest(String url, Object body, String token) {
        return post(url).contentType(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(body)).header(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix().concat(" ").concat(token));
    }

    private MockHttpServletRequestBuilder buildGetRequest(String url, Integer page, String token) {
        return get(url).param("page", page.toString()).contentType(MediaType.APPLICATION_JSON)
                .header(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix().concat(" ").concat(token));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        productRepository.deleteAll();
    }

}
