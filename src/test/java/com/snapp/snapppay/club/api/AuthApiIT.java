package com.snapp.snapppay.club.api;

import com.snapp.snapppay.club.domain.request.LoginRequest;
import com.snapp.snapppay.club.domain.request.UserRegisterRequest;
import com.snapp.snapppay.club.exception.ExceptionMessageCode;
import com.snapp.snapppay.club.repository.UserRepository;
import com.snapp.snapppay.club.test.BaseIntegrationTest;
import com.snapp.snapppay.club.test.UserTestObjectContainer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration tests for Auth API")
@Tag("integration")
@ActiveProfiles("test")
class AuthApiIT extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        insertDefaultUser();
    }

    @Test
    @SneakyThrows
    void testLoginSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(UserTestObjectContainer.DEFAULT_USERNAME);
        loginRequest.setPassword(UserTestObjectContainer.DEFAULT_USER_PASSWORD);
        mockMvc.perform(buildLoginRequest(loginRequest)).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testLoginDenied() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(UserTestObjectContainer.DEFAULT_USERNAME);
        loginRequest.setPassword(UserTestObjectContainer.INVALID_USER_PASSWORD);
        mockMvc.perform(buildLoginRequest(loginRequest)).andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    void testRegisterSuccess() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUsername("user1");
        userRegisterRequest.setPassword("userTestPassword");
        userRegisterRequest.setNationalCode("1234567812");
        mockMvc.perform(buildRegisterRequest(userRegisterRequest)).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testDuplicateUsernameError() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUsername("user");
        userRegisterRequest.setPassword("userTestPassword");
        userRegisterRequest.setNationalCode("1234567812");
        mockMvc.perform(buildRegisterRequest(userRegisterRequest)).andExpect(status().isBadRequest()).
                andExpect(content().string(ExceptionMessageCode.USERNAME_IS_DUPLICATE));
    }

    @Test
    @SneakyThrows
    void testDuplicateNationalCodeError() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUsername("user1");
        userRegisterRequest.setPassword("userTestPassword");
        userRegisterRequest.setNationalCode("1111111111");
        mockMvc.perform(buildRegisterRequest(userRegisterRequest)).andExpect(status().isBadRequest()).
                andExpect(content().string(ExceptionMessageCode.NATIONAL_CODE_IS_DUPLICATE));
    }

    protected MockHttpServletRequestBuilder buildRegisterRequest(UserRegisterRequest request) {
        return buildPostRequest("/api/auth/register", request);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

}
