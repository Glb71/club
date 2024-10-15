package com.snapp.snapppay.club.service.score;

import com.snapp.snapppay.club.domain.entity.Score;
import com.snapp.snapppay.club.domain.entity.UserAccount;
import com.snapp.snapppay.club.domain.request.AddScoreRequest;
import com.snapp.snapppay.club.repository.ScoreRepository;
import com.snapp.snapppay.club.service.userAccount.UserAccountProvider;
import com.snapp.snapppay.club.service.userScore.UserScoreUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private final UserAccountProvider userAccountProvider;
    private final ScoreRepository scoreRepository;
    private final UserScoreUpdater userScoreUpdater;

    @Transactional
    @Override
    public void add(AddScoreRequest addScoreRequest) {
        UserAccount userAccount = userAccountProvider.findOrCreate(addScoreRequest.getUsername());
        Score score = new Score();
        score.setScore(addScoreRequest.getScoreAmount());
        score.setCause(addScoreRequest.getCause());
        score.setUserAccount(userAccount);
        scoreRepository.save(score);
        userScoreUpdater.increase(score);
    }
}
