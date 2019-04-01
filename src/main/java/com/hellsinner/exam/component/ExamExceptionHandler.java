package com.hellsinner.exam.component;

import com.hellsinner.exam.model.web.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ExamExceptionHandler {

    @ExceptionHandler(value = {ExamException.class})
    public Result handleExamException(ExamException e, HttpServletRequest request,
                                  HttpServletResponse response) {
        return Result.build(e.getErrorCode(),e.getMessage());
    }

    @ExceptionHandler(value = {Throwable.class})
    public Result handleException(Throwable e, HttpServletRequest request,
                                  HttpServletResponse response){
        return Result.failed(ExamException.ExamExceptionEnum.SERVER_ERROR);
    }
}
