package com.kid.anh_thunh_nas.controller;

import com.kid.anh_thunh_nas.model.dto.response.BaseApiResponse;
import com.kid.anh_thunh_nas.model.dto.response.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController {
    @PostMapping
    public ResponseEntity<BaseApiResponse<CategoryResponse>> addNewCategory() {
        return null;
    }
}
