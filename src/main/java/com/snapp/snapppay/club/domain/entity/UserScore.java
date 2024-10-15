package com.snapp.snapppay.club.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "userScore")
@Table(name = "user_score")
public class UserScore extends EntityStructure {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "total_score")
    private Integer totalScore;
    @Column(name = "current_score")
    private Integer currentScore;
    @Column(name = "used_score")
    private Integer usedScore;

}
