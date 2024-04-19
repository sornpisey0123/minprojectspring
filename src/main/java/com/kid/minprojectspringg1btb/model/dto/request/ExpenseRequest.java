package com.kid.minprojectspringg1btb.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExpenseRequest {
    @NotNull
    private Double amount;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private Date date = new Date(System.currentTimeMillis());

    @NotNull
    private Integer categoryId;

}
