package com.kid.anh_thunh_nas.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BaseApiResponse<T> {
    private String message;
    private T payload;
    private HttpStatus status;
    private Timestamp time;
}
