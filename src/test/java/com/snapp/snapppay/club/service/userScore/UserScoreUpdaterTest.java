package com.snapp.snapppay.club.service.userScore;

import com.snapp.snapppay.club.domain.entity.Score;
import com.snapp.snapppay.club.domain.entity.UserAccount;
import com.snapp.snapppay.club.domain.entity.UserScore;
import com.snapp.snapppay.club.repository.UserScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserScoreUpdaterTest {

    private UserScoreUpdater userScoreUpdater;
    private Score score;
    private UserScore userScore;
    @Mock
    private UserScoreProvider userScoreProvider;
    @Mock
    private UserScoreRepository userScoreRepository;
    @Captor
    private ArgumentCaptor<UserScore> captor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userScoreUpdater = new UserScoreUpdaterImpl(userScoreProvider, userScoreRepository);
        score = new Score();
        score.setUserAccount(new UserAccount());
        score.setScore(12);
        userScore = new UserScore();
        userScore.setTotalScore(50);
        userScore.setCurrentScore(30);
        userScore.setUsedScore(20);
        when(userScoreProvider.findOrCreate(any())).thenReturn(userScore);
    }

    @Test
    void increase() {
        userScoreUpdater.increase(score);
        verify(userScoreRepository).save(captor.capture());
        UserScore savedUserScore = captor.getValue();
        Assertions.assertEquals(62, savedUserScore.getTotalScore(), "total score is not calculated correct");
        Assertions.assertEquals(42, savedUserScore.getCurrentScore(), "current score is not calculated correct");
        Assertions.assertEquals(20, savedUserScore.getUsedScore(), "used score must not change");
    }
}