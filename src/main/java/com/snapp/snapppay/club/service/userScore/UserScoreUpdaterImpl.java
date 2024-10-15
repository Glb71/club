package com.snapp.snapppay.club.service.userScore;

import com.snapp.snapppay.club.domain.entity.Score;
import com.snapp.snapppay.club.domain.entity.UserScore;
import com.snapp.snapppay.club.repository.UserScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserScoreUpdaterImpl implements UserScoreUpdater {

    private final UserScoreProvider userScoreProvider;
    private final UserScoreRepository userScoreRepository;

    @Override
    public void increase(Score score) {
        UserScore userScore = userScoreProvider.findOrCreate(score.getUserAccount().getUser());
        userScore.addScore(score.getScore());
        userScoreRepository.save(userScore);
    }
}
