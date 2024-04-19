package com.kid.minprojectspringg1btb.service.serviceImpl;

import com.kid.minprojectspringg1btb.model.dto.request.CategoryRequest;
import com.kid.minprojectspringg1btb.model.dto.response.CategoryResponse;
import com.kid.minprojectspringg1btb.repository.CategoryRepository;
import com.kid.minprojectspringg1btb.repository.UserRepository;
import com.kid.minprojectspringg1btb.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Integer addNewCategory(CategoryRequest categoryRequest, String email) {
        Integer userId = userRepository.getUserIdByEmail(email);
        return categoryRepository.addNewCategory(categoryRequest, userId);
    }

    @Override
    public CategoryResponse getCategoryById(Integer categoryId) {
        return categoryRepository.getCategoryById(categoryId);
    }
}