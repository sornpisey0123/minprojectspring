package com.kid.minprojectspringg1btb.repository;

import com.kid.minprojectspringg1btb.model.dto.request.ExpenseRequest;
import com.kid.minprojectspringg1btb.model.entity.Expense;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface ExpenseRepository {
    @Select("""
            INSERT INTO expenses (amount, description, date, user_id, category_id)
            VALUES (#{req.amount}, #{req.description}, #{req.date}, #{userId}, #{req.categoryId})
            RETURNING expense_id
            """)
    Integer addNewExpense(@Param("req") ExpenseRequest expenseRequest, Integer userId);

    @Select("""
                SELECT * FROM expenses WHERE expense_id = #{expenseId}
            """)
    @Results(
            id = "expenseMapper",
            value = {
                    @Result(property = "expenseId", column = "expense_id"),
                    @Result(property = "user", column = "user_id",
                            one = @One(select = "com.kid.minprojectspringg1btb.repository.UserRepository.findUserCategoryById")
                    ),
                    @Result(property = "category", column = "category_id",
                            one = @One(select = "com.kid.minprojectspringg1btb.repository.CategoryRepository.getCategoryExpenseById"))
            }
    )
    Expense getExpenseById(Integer expenseId);

    @Select("""
            SELECT * FROM expenses  ORDER BY ${sortBy} ${orderBy} 
             LIMIT #{size} OFFSET #{size} *(#{page} - 1) 
            """)
    @ResultMap("expenseMapper")
    List<Expense> getAllExpenses(Integer page, Integer size,String sortBy,String orderBy);

    @Delete("DELETE FROM expenses WHERE expense_id = #{expenseId}")
    Boolean removeExpenseById(Integer expenseId);

    @Select("UPDATE expenses " +
            "SET amount = #{req.amount}, description = #{req.description},date = #{req.date},category_id = #{req.categoryId} " +
            "WHERE expense_id = #{expenseId} " +
            "RETURNING expense_id")
    Integer editExpenseById(@Param("req") ExpenseRequest expenseRequest,Integer expenseId);
}
