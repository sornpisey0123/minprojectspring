package com.kid.minprojectspringg1btb.controller;

import com.kid.minprojectspringg1btb.model.dto.request.CategoryRequest;
import com.kid.minprojectspringg1btb.model.dto.response.BaseApiResponse;
import com.kid.minprojectspringg1btb.model.dto.response.CategoryResponse;
import com.kid.minprojectspringg1btb.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/v1/categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoriesController {

    private final CategoryService categoryService;

    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    String getUsernameOfCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUsername();
    }
    @PostMapping
    public ResponseEntity<BaseApiResponse<CategoryResponse>> addNewCategory( @Valid  @RequestBody CategoryRequest categoryRequest) {
        String userEmail = getUsernameOfCurrentUser();
        Integer categoryId = categoryService.addNewCategory(categoryRequest, userEmail);
        BaseApiResponse<CategoryResponse> response = BaseApiResponse.<CategoryResponse>builder()
                .message("The category has been successfully created.")
                .payload(categoryService.getCategoryById(categoryId))
                .status(HttpStatus.CREATED)
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}