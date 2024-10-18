package com.snapp.snapppay.club.domain.entity;

import com.snapp.snapppay.club.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "user_role")
@Table(name = "user_role")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends EntityStructure {

    @Enumerated(value = EnumType.STRING)
    private Roles role;

}
