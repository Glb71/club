package com.snapp.snapppay.club.service.userScore;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserScore;
import com.snapp.snapppay.club.repository.UserScoreRepository;
import com.snapp.snapppay.club.service.user.UserLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class UserScoreLoaderTest {

    private UserScoreLoader userScoreLoader;
    private User user;
    @Mock
    private UserScoreRepository userScoreRepository;
    @Mock
    private UserLoader userLoader;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userScoreLoader = new UserScoreLoaderImpl(userScoreRepository, userLoader);
        user = new User();
        when(userLoader.current()).thenReturn(user);
    }

    @Test
    void testFind() {
        UserScore repositoryScore = new UserScore();
        when(userScoreRepository.findByUser_Id(user.getId())).thenReturn(Optional.of(repositoryScore));
        UserScore returnedUserScore = userScoreLoader.current();
        assertNotNull(returnedUserScore, "return score must not be null");
        assertEquals(repositoryScore, returnedUserScore, "must return what found in repository");
    }

    @Test
    void testCreate() {
        when(userScoreRepository.findByUser_Id(user.getId())).thenReturn(Optional.empty());
        UserScore returnedUserScore = userScoreLoader.current();
        assertNotNull(returnedUserScore, "return score must not be null");
        assertEquals(user, returnedUserScore.getUser(), "current user must set to created user score");
    }

}