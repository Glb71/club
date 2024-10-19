package com.snapp.snapppay.club.service.userScore;

import com.snapp.snapppay.club.domain.entity.*;

public interface UserScoreUpdater {

    void increase(ScoreUp score);

    void reduce(ScoreDown score);

}
