package com.snapp.snapppay.club.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "userAccount")
@Table(name = "user_account")
public class UserAccount extends EntityStructure {

    @ManyToOne(optional = false)
    @JoinColumn(name = "provider_id")
    private Provider provider;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

}
