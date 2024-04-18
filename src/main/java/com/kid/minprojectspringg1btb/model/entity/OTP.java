package com.kid.minprojectspringg1btb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OTP {
    private Integer optId;
    private Date issuedAt;
    private Boolean expiration;
    private Boolean verify;
    private Integer userId;
}
