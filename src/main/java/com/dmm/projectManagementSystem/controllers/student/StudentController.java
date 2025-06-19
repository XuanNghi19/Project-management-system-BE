package com.dmm.projectManagementSystem.controllers.student;

import com.dmm.projectManagementSystem.dto.user.UserResponse;
import com.dmm.projectManagementSystem.service.student.studentService.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/student")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/information")
    public ResponseEntity<UserResponse> getCurrentUser(@RequestHeader("Authorization") String token) {
        UserResponse userResponse = studentService.handleGetCurrentStudent(token);
        return ResponseEntity.ok(userResponse);
    }

}
