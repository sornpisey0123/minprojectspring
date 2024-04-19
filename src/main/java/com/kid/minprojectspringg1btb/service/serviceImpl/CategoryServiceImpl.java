package com.kid.minprojectspringg1btb.service.serviceImpl;

import com.kid.minprojectspringg1btb.exception.NotFoundExceptionCustom;
import com.kid.minprojectspringg1btb.model.dto.request.CategoryRequest;
import com.kid.minprojectspringg1btb.model.dto.response.CategoryResponse;
import com.kid.minprojectspringg1btb.repository.CategoryRepository;
import com.kid.minprojectspringg1btb.repository.UserRepository;
import com.kid.minprojectspringg1btb.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    //create
    @Override
    public Integer addNewCategory(CategoryRequest categoryRequest, String email) {
        Integer userId = userRepository.getUserIdByEmail(email);
        return categoryRepository.addNewCategory(categoryRequest, userId);
    }

    //get by id
    @Override
    public CategoryResponse getCategoryById(Integer categoryId) {
        if (categoryRepository.getCategoryById(categoryId) == null){
            throw new NotFoundExceptionCustom("Category "+categoryId+ " Not Found");
        }
        return categoryRepository.getCategoryById(categoryId);
    }

    //delete
    @Override
    public Boolean deleteCategoryById(Integer categoryId) {
        if (categoryRepository.getCategoryById(categoryId) == null){
            throw new NotFoundExceptionCustom("Category "+categoryId+ " Not Found");
        }
        return categoryRepository.removeCategory(categoryId);
    }

    //update
    @Override
    public Integer updateCategoryById(CategoryRequest categoryRequest, Integer categoryId) {
        return categoryRepository.editCategory(categoryRequest,categoryId);
    }

    //get all
    @Override
    public List<CategoryResponse> getAllCategories(Integer page, Integer size) {
        return categoryRepository.getCategoryList(page,size);
    }
}