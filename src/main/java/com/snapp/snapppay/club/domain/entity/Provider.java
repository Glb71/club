package com.snapp.snapppay.club.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "provider")
@Table(name = "provider")
public class Provider extends EntityStructure {

    private String title;
    private String url;
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

}
