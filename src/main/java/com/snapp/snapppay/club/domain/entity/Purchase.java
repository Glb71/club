package com.snapp.snapppay.club.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "purchase")
@Table(name = "purchase")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase extends EntityStructure implements ScoreDown {

    @Column(name = "used_score", nullable = false)
    private Integer usedScore;
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public Integer getScore() {
        return usedScore;
    }
}
