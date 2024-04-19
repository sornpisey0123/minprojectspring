package com.kid.minprojectspringg1btb.service.serviceImpl;

import com.kid.minprojectspringg1btb.exception.NotFoundExceptionCustom;
import com.kid.minprojectspringg1btb.model.dto.request.ExpenseRequest;
import com.kid.minprojectspringg1btb.model.dto.request.UserRequest;
import com.kid.minprojectspringg1btb.model.dto.response.CategoryResponse;
import com.kid.minprojectspringg1btb.model.entity.Expense;
import com.kid.minprojectspringg1btb.repository.CategoryRepository;
import com.kid.minprojectspringg1btb.repository.ExpenseRepository;
import com.kid.minprojectspringg1btb.repository.UserRepository;
import com.kid.minprojectspringg1btb.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Integer addNewExpense(ExpenseRequest expenseRequest, String email) {
        Integer  userId = userRepository.getUserIdByEmail(email);
//        if (categoryRepository.getCategoryById(expenseRepository.getExpenseById()))
        // exception category id

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
        if (expenseRepository.getExpenseById(expenseId) == null){
            throw new NotFoundExceptionCustom("Expense id "+expenseId+ " Not Found");
        }
        return expenseRepository.removeExpenseById(expenseId);
    }

    @Override
    public List<Expense> getAllExpense(Integer page, Integer size, String sortBy, Boolean orderBy) {
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
        return expenseRepository.editExpenseById(expenseRequest,expenseId);
    }


}
