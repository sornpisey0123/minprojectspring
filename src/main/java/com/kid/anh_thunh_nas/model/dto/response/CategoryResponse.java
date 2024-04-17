package com.kid.anh_thunh_nas.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryResponse {
    private String categoryId;
    private String name;
    private String description;
    private UserResponse user;
}
