package com.snapp.snapppay.club.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "user_role")
@Table(
        name = "user_role",
        indexes = {
                @Index(name = "idx_role_id", columnList = "role_id")
        })
public class UserRole extends EntityStructure {

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
