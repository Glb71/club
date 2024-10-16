package com.snapp.snapppay.club.domain.response;

import lombok.Data;

@Data
public class UserScoreResponse {

    private Integer totalScore = 0;
    private Integer currentScore = 0;
    private Integer usedScore = 0;

}
