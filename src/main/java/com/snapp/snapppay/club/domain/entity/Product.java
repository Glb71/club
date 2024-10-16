package com.snapp.snapppay.club.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "product")
@Table(name = "product")
public class Product extends EntityStructure {

    @Column(unique = true, nullable = false)
    private String title;
    @Column(columnDefinition = "text")
    private String description;
    private String image;
    @Column(nullable = false)
    private Boolean active = true;
    @Column(name = "score_price", nullable = false)
    private Integer scorePrice;

}
