package com.kid.minprojectspringg1btb.model.dto.response;

import com.kid.minprojectspringg1btb.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryResponse {
    private Integer categoryId;
    private String name;
    private String description;
    private User user;
}
