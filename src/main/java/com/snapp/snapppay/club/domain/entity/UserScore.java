package com.snapp.snapppay.club.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "userScore")
@Table(
        name = "user_score",
        indexes = {
                @Index(name = "idx_user_score_user", columnList = "user_id")
        })
public class UserScore extends EntityStructure {

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "total_score")
    private Integer totalScore = 0;
    @Column(name = "current_score")
    private Integer currentScore = 0;
    @Column(name = "used_score")
    private Integer usedScore = 0;

}
