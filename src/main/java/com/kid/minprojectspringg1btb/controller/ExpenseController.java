package com.kid.minprojectspringg1btb.controller;

import com.kid.minprojectspringg1btb.model.dto.request.ExpenseRequest;
import com.kid.minprojectspringg1btb.model.dto.response.BaseApiResponse;
import com.kid.minprojectspringg1btb.model.entity.Expense;
import com.kid.minprojectspringg1btb.service.ExpenseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
@SecurityRequirement(name = "bearerAuth")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    String getUsernameOfCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUsername();
    }
    @PostMapping
    public ResponseEntity<BaseApiResponse<Expense>> addNewExpense(@Valid @RequestBody ExpenseRequest expenseRequest) {
        String userEmail = getUsernameOfCurrentUser();
        Integer storeExpenseId = expenseService.addNewExpense(expenseRequest, userEmail);
        BaseApiResponse<Expense> response = BaseApiResponse.<Expense>builder()
                .message("The expense has been successfully created.")
                .payload(expenseService.getExpenseById(storeExpenseId))
                .status(HttpStatus.CREATED)
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Expense>> getExpenseById(@PathVariable("id") Integer expenseId){
        BaseApiResponse<Expense> response = BaseApiResponse.<Expense>builder()
                .message("The expense id "+expenseId+" has been successfully founded.")
                .payload(expenseService.getExpenseById(expenseId))
                .status(HttpStatus.OK)
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Expense>> deleteExpenseById(@PathVariable("id") Integer expenseId){
        Boolean storeId = expenseService.deleteExpenseById(expenseId);
        BaseApiResponse<Expense> response = null;
        if (storeId){
            response = BaseApiResponse.<Expense>builder()
                    .message("The expense has been successfully deleted.")
                    .status(HttpStatus.OK)
                    .time(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<BaseApiResponse<List<Expense>>> getAllExpense(
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "5") Integer size,
            @RequestParam(name = "sortBy",defaultValue = "expense_id") String sortBy,
            @RequestParam(name = "orderBy",defaultValue = "false") Boolean orderBy

    ){
        BaseApiResponse<List<Expense>> response = BaseApiResponse.<List<Expense>>builder()
                .message("All expenses have been successfully founded.")
                .payload(expenseService.getAllExpense(page,size,sortBy, orderBy))
                .status(HttpStatus.OK)
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Expense>> updateExpenseById(@RequestBody ExpenseRequest expenseRequest,@PathVariable("id") Integer expenseId){
        Integer storeExpenseId = expenseService.updateExpenseById(expenseRequest, expenseId);

        BaseApiResponse<Expense> response = BaseApiResponse.<Expense>builder()
                .message("The expense has been successfully updated.")
                .payload(expenseService.getExpenseById(storeExpenseId))
                .status(HttpStatus.CREATED)
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

}
