package com.snapp.snapppay.club.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "purchase")
@Table(
        name = "purchase",
        indexes = {
                @Index(name = "idx_purchase_user", columnList = "user_id"),
                @Index(name = "idx_purchase_product", columnList = "product_id")
        }
)
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
