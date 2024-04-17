package com.kid.anh_thunh_nas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(BadRequestExceptionCustom.class)
    public ProblemDetail handleBadRequestExceptionCustom (BadRequestExceptionCustom ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,  ex.getMessage());
        problemDetail.setTitle("Bad Request");
        return problemDetail;
    }

    @ExceptionHandler(NotFoundExceptionCustom.class)
    public ProblemDetail handleNotFoundExceptionCustom (NotFoundExceptionCustom ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,  ex.getMessage());
        problemDetail.setTitle("Not Found");
        return problemDetail;
    }
}
