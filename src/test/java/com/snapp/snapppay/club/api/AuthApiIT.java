package com.snapp.snapppay.club.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserRole;
import com.snapp.snapppay.club.domain.request.LoginRequest;
import com.snapp.snapppay.club.domain.request.UserRegisterRequest;
import com.snapp.snapppay.club.enums.Roles;
import com.snapp.snapppay.club.exception.ExceptionMessageCode;
import com.snapp.snapppay.club.repository.UserRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration tests for Auth API")
@Tag("integration")
@ActiveProfiles("test")
class AuthApiIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("userTestPassword"));
        user.setNationalCode("1111111111");
        UserRole userRole = new UserRole();
        userRole.setRole(Roles.USER);
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
    }

    @Test
    @SneakyThrows
    void testLoginSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("userTestPassword");
        mockMvc.perform(buildLoginRequest(loginRequest)).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testLoginDenied() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("invalidPassword");
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

    MockHttpServletRequestBuilder buildLoginRequest(LoginRequest request) {
        return buildPostRequest("/api/auth/login", request);
    }

    MockHttpServletRequestBuilder buildRegisterRequest(UserRegisterRequest request) {
        return buildPostRequest("/api/auth/register", request);
    }

    @SneakyThrows
    MockHttpServletRequestBuilder buildPostRequest(String url, Object body) {
        return post(url).contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(body));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

}
