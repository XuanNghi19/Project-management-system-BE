package com.dmm.projectManagementSystem.controllers.student;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.grade.StudentGradeResponse;
import com.dmm.projectManagementSystem.service.student.gradeService.StudentGradeService;
import com.dmm.projectManagementSystem.utils.annotation.ApiMessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/student/grade")
public class StudentGradeController {

    private final StudentGradeService studentGradeService;

    public StudentGradeController(StudentGradeService studentGradeService) {
        this.studentGradeService = studentGradeService;
    }

    @ApiMessageResponse(message = "Lấy điểm theo đề tài của sinh viên")
    @GetMapping("/by-student")
    public ResponseEntity<ApiResponseStudent<StudentGradeResponse>> getGradeByStudentId(
            @RequestParam Long studentId) {
        return ResponseEntity.ok(studentGradeService.getGradeByStudentId(studentId));
    }
}