package com.snapp.snapppay.club.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "score")
@Table(name = "score")
public class Score extends EntityStructure {

    @Column(nullable = false)
    private Integer score;
    @Column(nullable = false)
    private String cause;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

}
