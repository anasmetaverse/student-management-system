package com.ytseries.sms.controller.advice;

import com.ytseries.sms.dto.ResponseModel;
import com.ytseries.sms.exception.DuplicateExceptionResource;
import com.ytseries.sms.exception.NotFoundExceptionResource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {


    @ExceptionHandler(DuplicateExceptionResource.class)
    public ResponseModel handelDuplicateExceptionResource (DuplicateExceptionResource ex) {
        return ResponseModel.conflict(
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(NotFoundExceptionResource.class)
    public ResponseModel handelDuplicateExceptionResource (NotFoundExceptionResource ex) {
        return ResponseModel.not_found(
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseModel handelException (Exception ex) {
        return new ResponseModel(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An Unexpected Error Occured, Please try again",
                null
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseModel handelMethodArgumentNotValidException (MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMsg = error.getDefaultMessage();
                    errors.put(fieldName, errorMsg);
                });

        return new ResponseModel(
                HttpStatus.BAD_REQUEST,
                "Validation Error",
                errors
        );
    }
}
