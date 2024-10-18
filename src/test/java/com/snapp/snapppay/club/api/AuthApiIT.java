package com.snapp.snapppay.club.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserRole;
import com.snapp.snapppay.club.domain.request.LoginRequest;
import com.snapp.snapppay.club.domain.request.UserRegisterRequest;
import com.snapp.snapppay.club.enums.Roles;
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

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(loginRequest))).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testLoginDenied() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("invalidPassword");
        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(loginRequest))).andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    void testRegisterSuccess() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUsername("user1");
        userRegisterRequest.setPassword("userTestPassword");
        userRegisterRequest.setNationalCode("1234567812");
        mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(userRegisterRequest))).andExpect(status().isOk());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

}
