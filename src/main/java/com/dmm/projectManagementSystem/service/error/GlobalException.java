package com.dmm.projectManagementSystem.service.error;

import com.dmm.projectManagementSystem.controllers.student.ProjectController;
import com.dmm.projectManagementSystem.controllers.student.StudentNotificationController;
import com.dmm.projectManagementSystem.controllers.student.StudentTopicController;
import com.dmm.projectManagementSystem.controllers.student.TeamController;
import com.dmm.projectManagementSystem.dto.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice(basePackageClasses = {TeamController.class, ProjectController.class, StudentNotificationController.class, StudentTopicController.class})
public class GlobalException {
    @ExceptionHandler(value = {
            NoSuchElementException.class,
            IllegalStateException.class,
    })
    public ResponseEntity<RestResponse<Object>> handleException (Exception ex) {
        RestResponse<Object> response = new RestResponse<>();
        response.setMessage("Xảy ra lỗi......");
        response.setError(ex.getMessage());
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(response);
    }
}
