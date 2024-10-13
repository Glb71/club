package com.snapp.snapppay.club.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "user")
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_username", columnList = "username"),
                @Index(name = "idx_national_code", columnList = "national_code")
        })
public class User extends EntityStructure {

    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "national_code", nullable = false, unique = true)
    private String nationalCode;
    @Column(nullable = false)
    private Boolean active = true;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Set<UserRole> roles = new HashSet<>();
}
