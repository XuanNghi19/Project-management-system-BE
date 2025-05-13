package com.dmm.projectManagementSystem.exception;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = InvalidException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalitException(InvalidException invalidException){
        ApiResponse<Object> res=new ApiResponse<>();
        res.setCode(HttpStatus.BAD_REQUEST.value());
        res.setMessage(invalidException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
}
