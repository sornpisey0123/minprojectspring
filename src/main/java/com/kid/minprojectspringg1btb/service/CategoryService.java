package com.kid.minprojectspringg1btb.service;

import com.kid.minprojectspringg1btb.model.dto.request.CategoryRequest;
import com.kid.minprojectspringg1btb.model.dto.response.CategoryResponse;

public interface CategoryService {
    Integer addNewCategory(CategoryRequest categoryRequest, String email);
    CategoryResponse getCategoryById(Integer categoryId);
}
