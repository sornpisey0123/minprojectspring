package com.kid.minprojectspringg1btb.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryExpenseResponse {
    private Integer categoryId;
    private String name;
    private String description;
}
