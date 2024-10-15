package com.snapp.snapppay.club.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "provider")
@Table(
        name = "provider",
        indexes = {
                @Index(name = "idx_provider_user", columnList = "user_id")
        })
public class Provider extends EntityStructure {

    private String title;
    private String url;
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

}
