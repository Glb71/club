package com.snapp.snapppay.club.domain.entity;

import com.snapp.snapppay.club.enums.Roles;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "user_role")
@Table(name = "user_role")
public class UserRole extends EntityStructure {

    @Enumerated(value = EnumType.STRING)
    private Roles role;

}
