package com.snapp.snapppay.club.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "userScore")
@Table(
        name = "user_score",
        indexes = {
                @Index(name = "idx_user_score_user", columnList = "user_id")
        })
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserScore extends EntityStructure {

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "total_score")
    @Builder.Default
    private Integer totalScore = 0;
    @Column(name = "current_score")
    @Builder.Default
    private Integer currentScore = 0;
    @Column(name = "used_score")
    @Builder.Default
    private Integer usedScore = 0;

    public void addScore(Integer score) {
        this.currentScore += score;
        this.totalScore += score;
    }

}
