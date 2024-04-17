package com.kid.anh_thunh_nas.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AppFileResponse {
    private String fileName;
    private String type;
    private Long size;
}
