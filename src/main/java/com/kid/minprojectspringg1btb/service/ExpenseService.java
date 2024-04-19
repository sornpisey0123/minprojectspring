package com.kid.minprojectspringg1btb.service;

import com.kid.minprojectspringg1btb.model.dto.request.ExpenseRequest;
import com.kid.minprojectspringg1btb.model.entity.Expense;

import java.util.List;

public interface ExpenseService {
    Integer addNewExpense(ExpenseRequest expenseRequest, String email);
    Expense getExpenseById(Integer expenseId);

    Boolean deleteExpenseById(Integer expenseId);

    List<Expense> getAllExpense(Integer page , Integer size, String sortBy, Boolean orderBy );

    Integer updateExpenseById(ExpenseRequest expenseRequest,Integer expenseId);


}
