package com.snapp.snapppay.club.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "score")
@Table(
        name = "score",
        indexes = {
                @Index(name = "idx_score_user_account", columnList = "user_account_id")
        })
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Score extends EntityStructure {

    @Column(nullable = false)
    private Integer score;
    @Column(nullable = false)
    private String cause;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

}
