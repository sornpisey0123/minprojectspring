package com.kid.minprojectspringg1btb.service.serviceImpl;

import com.kid.minprojectspringg1btb.exception.BadRequestExceptionCustom;
import com.kid.minprojectspringg1btb.exception.NotFoundExceptionCustom;
import com.kid.minprojectspringg1btb.model.dto.request.ExpenseRequest;
import com.kid.minprojectspringg1btb.model.entity.Expense;
import com.kid.minprojectspringg1btb.repository.ExpenseRepository;
import com.kid.minprojectspringg1btb.repository.UserRepository;
import com.kid.minprojectspringg1btb.service.CategoryService;
import com.kid.minprojectspringg1btb.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    private final CategoryService categoryService;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserRepository userRepository, CategoryService categoryService) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Integer addNewExpense(ExpenseRequest expenseRequest, String email) {
        Integer  userId = userRepository.getUserIdByEmail(email);
        categoryService.getCategoryById(expenseRequest.getCategoryId());

        return expenseRepository.addNewExpense(expenseRequest,userId);
    }

    @Override
    public Expense getExpenseById(Integer expenseId) {
        if (expenseRepository.getExpenseById(expenseId) == null){
            throw new NotFoundExceptionCustom("Expense "+expenseId+ " Not Found");
        }
        return expenseRepository.getExpenseById(expenseId);
    }

    @Override
    public Boolean deleteExpenseById(Integer expenseId) {
        getExpenseById(expenseId);
        return expenseRepository.removeExpenseById(expenseId);
    }

    @Override
    public List<Expense> getAllExpense(Integer page, Integer size, String sortBy, Boolean orderBy) {
        if(page <= 0 && size <= 0){
            throw new BadRequestExceptionCustom("page & size must be greater than 0");
        }
        if (page <= 0){
            throw new BadRequestExceptionCustom("page must be greater than 0");
        }
        if (size <= 0){
            throw new BadRequestExceptionCustom("size must be greater than 0");
        }
        String orderString;
        if(orderBy){
             orderString = "desc";
        }else {
            orderString = "asc";
        }

        return expenseRepository.getAllExpenses(page,size,sortBy,orderString);
    }

    @Override
    public Integer updateExpenseById(ExpenseRequest expenseRequest, Integer expenseId) {
        getExpenseById(expenseId);
        categoryService.getCategoryById(expenseRequest.getCategoryId());
        return expenseRepository.editExpenseById(expenseRequest,expenseId);
    }


}
