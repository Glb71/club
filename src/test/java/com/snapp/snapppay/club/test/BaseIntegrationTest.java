package com.snapp.snapppay.club.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapp.snapppay.club.domain.entity.Provider;
import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.request.LoginRequest;
import com.snapp.snapppay.club.repository.*;
import com.snapp.snapppay.club.security.jwt.JwtConfig;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public abstract class BaseIntegrationTest {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ProviderRepository providerRepository;
    @Autowired
    protected UserAccountRepository userAccountRepository;
    @Autowired
    protected ScoreRepository scoreRepository;
    @Autowired
    protected UserScoreRepository userScoreRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected JwtConfig jwtConfig;

    private UserTestObjectContainer userTestObjectContainer;

    @PostConstruct
    void init() {
        userTestObjectContainer = new UserTestObjectContainer(passwordEncoder);
    }

    protected Provider insertDefaultProvider() {
        User defaultProvider = userTestObjectContainer.createDefaultProvider();
        userRepository.save(defaultProvider);
        Provider provider = Provider.builder()
                .user(defaultProvider)
                .title("provider")
                .build();
        providerRepository.save(provider);
        return provider;
    }

    protected Provider insertDefaultProvider2() {
        User defaultProvider = userTestObjectContainer.createDefaultProvider2();
        userRepository.save(defaultProvider);
        Provider provider = Provider.builder()
                .user(defaultProvider)
                .title("provider2")
                .build();
        providerRepository.save(provider);
        return provider;
    }

    protected User insertDefaultUser() {
        User defaultUser = userTestObjectContainer.createDefaultUser();
        userRepository.save(defaultUser);
        return defaultUser;
    }

    protected void insertDefaultAdmin() {
        User defaultAdmin = userTestObjectContainer.createDefaultAdmin();
        userRepository.save(defaultAdmin);
    }

    public String getDefaultUserToken() {
        return getToken(UserTestObjectContainer.DEFAULT_USERNAME, UserTestObjectContainer.DEFAULT_USER_PASSWORD);
    }

    public String getDefaultAdminToken() {
        return getToken(UserTestObjectContainer.DEFAULT_ADMIN_USERNAME, UserTestObjectContainer.DEFAULT_ADMIN_PASSWORD);
    }

    public String getDefaultProviderToken() {
        return getToken(UserTestObjectContainer.DEFAULT_PROVIDER_USERNAME, UserTestObjectContainer.DEFAULT_PROVIDER_PASSWORD);
    }

    public String getDefaultProvider2Token() {
        return getToken(UserTestObjectContainer.DEFAULT_PROVIDER2_USERNAME, UserTestObjectContainer.DEFAULT_PROVIDER2_PASSWORD);
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
    protected MockHttpServletRequestBuilder buildPostRequest(String url, Object body) {
        return post(url).contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(body));
    }

    protected MockHttpServletRequestBuilder buildPostRequest(String url, Object body, String token) {
        return buildPostRequest(url, body).header(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix().concat(" ").concat(token));
    }

    protected MockHttpServletRequestBuilder buildLoginRequest(LoginRequest request) {
        return buildPostRequest("/api/auth/login", request);
    }

    protected MockHttpServletRequestBuilder buildGetRequest(String url, String token) {
        return get(url).contentType(MediaType.APPLICATION_JSON)
                .header(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix().concat(" ").concat(token));
    }

}