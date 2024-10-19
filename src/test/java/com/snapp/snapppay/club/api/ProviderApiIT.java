package com.snapp.snapppay.club.api;

import com.snapp.snapppay.club.domain.entity.Provider;
import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserScore;
import com.snapp.snapppay.club.domain.request.AddScoreRequest;
import com.snapp.snapppay.club.domain.request.ProviderRegisterRequest;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration tests for Provider API")
@Tag("integration")
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProviderApiIT extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private Provider defaultProvider;
    private User defaultUser;
    private String adminToken;
    private String userToken;
    private String providerToken;
    private String provider2Token;

    @BeforeEach
    void setup() {
        defaultUser = insertDefaultUser();
        insertDefaultAdmin();
        adminToken = getDefaultAdminToken();
        userToken = getDefaultUserToken();
        defaultProvider = insertDefaultProvider();
        providerToken = getDefaultProviderToken();
        insertDefaultProvider2();
        provider2Token = getDefaultProvider2Token();
    }

    @Test
    @SneakyThrows
    void testAddSuccess() {
        ProviderRegisterRequest providerRegisterRequest = new ProviderRegisterRequest();
        providerRegisterRequest.setUserId(defaultUser.getId());
        providerRegisterRequest.setTitle("providerTitle");
        mockMvc.perform(buildPostRequest("/api/provider/add", providerRegisterRequest, adminToken)).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testDuplicateTitle() {
        ProviderRegisterRequest providerRegisterRequest = new ProviderRegisterRequest();
        providerRegisterRequest.setUserId(defaultUser.getId());
        providerRegisterRequest.setTitle(defaultProvider.getTitle());
        mockMvc.perform(buildPostRequest("/api/provider/add", providerRegisterRequest, adminToken)).andExpect(status().isBadRequest()).andExpect(content().string(ExceptionMessageCode.PROVIDER_TITLE_IS_DUPLICATE));
    }

    @Test
    @SneakyThrows
    void testDuplicateUser() {
        ProviderRegisterRequest providerRegisterRequest = new ProviderRegisterRequest();
        providerRegisterRequest.setUserId(defaultProvider.getUser().getId());
        providerRegisterRequest.setTitle("new title");
        mockMvc.perform(buildPostRequest("/api/provider/add", providerRegisterRequest, adminToken)).andExpect(status().isBadRequest()).andExpect(content().string(ExceptionMessageCode.THIS_USER_IS_ALREADY_A_PROVIDER));
    }

    @Test
    @SneakyThrows
    void testAddAccess() {
        ProviderRegisterRequest providerRegisterRequest = new ProviderRegisterRequest();
        providerRegisterRequest.setUserId(defaultUser.getId());
        providerRegisterRequest.setTitle("new title");
        mockMvc.perform(buildPostRequest("/api/provider/add", providerRegisterRequest, userToken)).andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void testAddScoreSuccess() {
        AddScoreRequest scoreRequest = AddScoreRequest.builder().username(defaultUser.getUsername()).scoreAmount(100).cause("cause").build();
        mockMvc.perform(buildPostRequest("/api/provider/addScore", scoreRequest, providerToken)).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testConcurrentAddScore() {
        int counter = 0;
        List<Thread> threads = new ArrayList<>();
        AddScoreRequest scoreRequest = AddScoreRequest.builder().username(defaultUser.getUsername()).scoreAmount(1).cause("cause").build();
        while (counter < 100) {
            Thread thread = new Thread(() -> {
                try {
                    mockMvc.perform(buildPostRequest("/api/provider/addScore", scoreRequest, providerToken)).andExpect(status().isOk());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            threads.add(thread);
            Thread thread2 = new Thread(() -> {
                try {
                    mockMvc.perform(buildPostRequest("/api/provider/addScore", scoreRequest, provider2Token)).andExpect(status().isOk());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            threads.add(thread2);
            counter++;
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        Optional<UserScore> userScore = userScoreRepository.findByUser_Id(defaultUser.getId());
        assertNotNull(userScore.orElse(null));
        assertEquals(200, userScore.get().getTotalScore());
    }

    @Test
    @SneakyThrows
    void testAddScoreAccess() {
        AddScoreRequest scoreRequest = AddScoreRequest.builder().username(
                defaultUser.getUsername()).scoreAmount(100).cause("cause").build();
        mockMvc.perform(buildPostRequest("/api/provider/addScore", scoreRequest, userToken)).andExpect(status().isForbidden());

    }


}
