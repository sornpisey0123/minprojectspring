package com.kid.minprojectspringg1btb.controller;

import com.kid.minprojectspringg1btb.model.dto.request.CategoryRequest;
import com.kid.minprojectspringg1btb.model.dto.response.BaseApiResponse;
import com.kid.minprojectspringg1btb.model.dto.response.CategoryResponse;
import com.kid.minprojectspringg1btb.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

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

    //create
    @PostMapping
    public ResponseEntity<BaseApiResponse<CategoryResponse>> addNewCategory( @Valid  @RequestBody CategoryRequest categoryRequest) {
        String userEmail = getUsernameOfCurrentUser();
        Integer storeCategoryId = categoryService.addNewCategory(categoryRequest, userEmail);
        BaseApiResponse<CategoryResponse> response = BaseApiResponse.<CategoryResponse>builder()
                .message("The category has been successfully created.")
                .payload(categoryService.getCategoryById(storeCategoryId))
                .status(HttpStatus.CREATED)
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //get by id
    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<CategoryResponse>> getCategoryById(@PathVariable("id") Integer categoryId) {
        BaseApiResponse<CategoryResponse> response = BaseApiResponse.<CategoryResponse>builder()
                .message("The category id "+ categoryId +" has been founded .")
                .payload(categoryService.getCategoryById(categoryId))
                .status(HttpStatus.OK)
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //delete
    @DeleteMapping("{id}")
    public ResponseEntity<BaseApiResponse<CategoryResponse>> deleteCategoryById(@PathVariable("id") Integer categoryId) {
        Boolean storeCategoryId = categoryService.deleteCategoryById(categoryId);
        BaseApiResponse<CategoryResponse> response = null;
        if (storeCategoryId){
            response = BaseApiResponse.<CategoryResponse>builder()
                    .message("The category has been successfully created.")
                    .status(HttpStatus.OK)
                    .time(new Timestamp(System.currentTimeMillis()))
                    .build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    //update
    @PutMapping("{id}")
    public ResponseEntity<BaseApiResponse<CategoryResponse>> updateCategoryById(@PathVariable("id") Integer categoryId, @Valid @RequestBody CategoryRequest categoryRequest) {
        BaseApiResponse<CategoryResponse> response = null;
        Integer storeCategoryId = categoryService.updateCategoryById(categoryRequest, categoryId);
        response = BaseApiResponse.<CategoryResponse>builder()
                .message("The category id "+categoryId+" has been successfully updated.")
                .payload(categoryService.getCategoryById(storeCategoryId))
                .status(HttpStatus.CREATED)
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //get all
    @GetMapping()
    public ResponseEntity<BaseApiResponse<List<CategoryResponse>>> getCategories(
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "5") Integer size
    ) {
        BaseApiResponse<List<CategoryResponse>> response = BaseApiResponse.<List<CategoryResponse>>builder()
                .message("All categories have been successfully founded.💓")
                .payload(categoryService.getAllCategories(page, size))
                .status(HttpStatus.OK)
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }
}