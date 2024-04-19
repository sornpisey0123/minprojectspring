package com.kid.minprojectspringg1btb.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FileUploadResponse<T>{
    private String message;
    private HttpStatus httpStatus;
    private T payload;
}
