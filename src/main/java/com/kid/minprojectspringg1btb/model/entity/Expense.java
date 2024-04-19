package com.kid.minprojectspringg1btb.model.entity;

import com.kid.minprojectspringg1btb.model.dto.response.CategoryExpenseResponse;
import com.kid.minprojectspringg1btb.model.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Expense {

    private Integer expenseId;
    private Double amount;
    private String description;
    private Date date;
    private UserResponse user;
    private CategoryExpenseResponse category;
}
