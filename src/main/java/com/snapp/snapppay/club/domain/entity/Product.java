package com.snapp.snapppay.club.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "product")
@Table(name = "product")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends EntityStructure {

    @Column(unique = true, nullable = false)
    private String title;
    @Column(columnDefinition = "text")
    private String description;
    private String image;
    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
    @Column(name = "score_price", nullable = false)
    private Integer scorePrice;

}
