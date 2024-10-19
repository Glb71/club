package com.snapp.snapppay.club.service.userScore;

import com.snapp.snapppay.club.domain.entity.ScoreDown;
import com.snapp.snapppay.club.domain.entity.ScoreUp;
import com.snapp.snapppay.club.domain.entity.UserScore;
import com.snapp.snapppay.club.exception.ExceptionMessageCode;
import com.snapp.snapppay.club.exception.ValidationException;
import com.snapp.snapppay.club.repository.UserScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserScoreUpdaterImpl implements UserScoreUpdater {

    private final UserScoreProvider userScoreProvider;
    private final UserScoreRepository userScoreRepository;

    @Override
    public void increase(ScoreUp score) {
        UserScore userScore = userScoreProvider.findOrCreate(score.getUser());
        userScore.addScore(score.getScore());
        userScoreRepository.save(userScore);
    }

    @Override
    public void reduce(ScoreDown score) {
        UserScore userScore = userScoreProvider.findOrCreate(score.getUser());
        userScore.reduceScore(score.getScore());
        if (userScore.getCurrentScore().compareTo(0) < 0) {
            throw new ValidationException(ExceptionMessageCode.INSUFFICIENT_SCORE);
        }
        userScoreRepository.save(userScore);
    }
}
