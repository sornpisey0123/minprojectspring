package com.kid.anh_thunh_nas.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
    private String userId;
    private String email;
    private String password;
    private String profileImage;
}
