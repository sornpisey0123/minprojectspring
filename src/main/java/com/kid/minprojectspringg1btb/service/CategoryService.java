package com.kid.minprojectspringg1btb.service;

import com.kid.minprojectspringg1btb.model.dto.request.CategoryRequest;
import com.kid.minprojectspringg1btb.model.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    //create
    Integer addNewCategory(CategoryRequest categoryRequest, String email);

    //get by id
    CategoryResponse getCategoryById(Integer categoryId);

    //delete
    Boolean deleteCategoryById(Integer categoryId);

    //update
    Integer updateCategoryById(CategoryRequest categoryRequest,Integer categoryId);

    //get all
    List<CategoryResponse> getAllCategories(Integer page,Integer size);
}
