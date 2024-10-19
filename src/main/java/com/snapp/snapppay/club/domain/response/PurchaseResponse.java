package com.snapp.snapppay.club.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Schema(name = "purchase response")
public class PurchaseResponse {

    private String product;
    private Timestamp date;
    private Integer usedScore;

}
