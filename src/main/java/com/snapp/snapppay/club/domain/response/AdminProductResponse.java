package com.snapp.snapppay.club.domain.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AdminProductResponse {

    private Long id;
    private String title;
    private String description;
    private String image;
    private Integer scoreAmount;
    private String creator;
    private String updater;
    private Timestamp creationTime;
    private Timestamp lastUpdateTime;
    private Long version;

}
