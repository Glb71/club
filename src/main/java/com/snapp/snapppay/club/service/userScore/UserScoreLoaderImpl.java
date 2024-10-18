package com.snapp.snapppay.club.service.userScore;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserScore;
import com.snapp.snapppay.club.repository.UserScoreRepository;
import com.snapp.snapppay.club.service.user.UserLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserScoreLoaderImpl implements UserScoreLoader {

    private final UserScoreRepository userScoreRepository;
    private final UserLoader userLoader;

    @Override
    public UserScore current() {
        User user = userLoader.current();
        return userScoreRepository.findByUser_Id(user.getId()).orElseGet(() -> create(user));
    }

    private UserScore create(User user) {
        return UserScore.builder()
                .user(user)
                .build();
    }
}
