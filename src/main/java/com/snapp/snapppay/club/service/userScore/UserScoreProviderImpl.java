package com.snapp.snapppay.club.service.userScore;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserScore;
import com.snapp.snapppay.club.repository.ConcurrentUserScoreRepository;
import com.snapp.snapppay.club.service.user.UserLocker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserScoreProviderImpl implements UserScoreProvider {

    private final ConcurrentUserScoreRepository userScoreRepository;
    private final UserLocker userLocker;

    @Override
    public UserScore findOrCreate(User user) {
        return userScoreRepository.findByUser_Id(user.getId()).orElseGet(() -> create(user));
    }

    private UserScore create(User user) {
        userLocker.lock(user.getId());
        return userScoreRepository.findByUser_Id(user.getId()).orElseGet(() -> {
            UserScore userScore = new UserScore();
            userScore.setUser(user);
            userScoreRepository.save(userScore);
            return userScore;
        });
    }
}
