package com.snapp.snapppay.club.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "role")
@Table(
        name = "role",
        indexes = {
                @Index(name = "idx_role_code", columnList = "role_code")
        })
public class Role extends EntityStructure {

    @Column(name = "role_code", nullable = false, unique = true)
    private String roleCode;

    @Column(name = "description")
    private String description;

}
