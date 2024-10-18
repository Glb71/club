package com.snapp.snapppay.club.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "userAccount")
@Table(
        name = "user_account",
        indexes = {
                @Index(name = "idx_user_account_provider", columnList = "provider_id"),
                @Index(name = "idx_user_account_user", columnList = "user_id")
        })
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount extends EntityStructure {

    @ManyToOne(optional = false)
    @JoinColumn(name = "provider_id")
    private Provider provider;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

}
