package com.snapp.snapppay.club.service.userScore;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserScore;
import com.snapp.snapppay.club.repository.ConcurrentUserScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserScoreProviderTest {

    private UserScoreProvider userScoreProvider;
    private User user;
    @Mock
    private ConcurrentUserScoreRepository concurrentUserScoreRepository;


    @BeforeEach
    void setup() {
        userScoreProvider = new UserScoreProviderImpl(concurrentUserScoreRepository);
        user = new User();
    }

    @Test
    void testFind() {
        UserScore repositoryUserScore = new UserScore();
        when(concurrentUserScoreRepository.findByUser_Id(user.getId())).thenReturn(Optional.of(repositoryUserScore));
        UserScore result = userScoreProvider.findOrCreate(user);
        try {
            verify(concurrentUserScoreRepository, times(0)).save(any(UserScore.class));
        } catch (AssertionError e) {
            fail("should not create a new user score where user score exists in database");
        }
        assertNotNull(result, "user score should not be null");
        assertEquals(repositoryUserScore, result,
                "if repository find a user account should not create a new one");
    }

    @Test
    void testCreate() {
        when(concurrentUserScoreRepository.findByUser_Id(user.getId())).thenReturn(Optional.empty());
        UserScore result = userScoreProvider.findOrCreate(user);
        try {
            verify(concurrentUserScoreRepository, times(1)).save(any(UserScore.class));
        } catch (AssertionError e) {
            fail("new user account must be saved when user account is not available");
        }
        assertNotNull(result, "user account should not be null");
        assertEquals(user, result.getUser(), "user must be equals to found user");
    }

}
