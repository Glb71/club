package com.snapp.snapppay.club.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class EntityStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedBy
    @Column(name = "insert_user")
    private Long insertUser;

    @LastModifiedBy
    @Column(name = "update_user")
    private Long updateUser;

    @CreatedDate
    @Column(name = "insert_date_time")
    private Timestamp insertDateTime;

    @LastModifiedDate
    @Column(name = "update_date_time")
    private Timestamp updateDateTime;

    @Version
    private Long version;

}
