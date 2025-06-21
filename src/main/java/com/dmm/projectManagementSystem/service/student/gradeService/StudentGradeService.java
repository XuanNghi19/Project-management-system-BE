package com.dmm.projectManagementSystem.service.student.gradeService;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.grade.StudentGradeResponse;

public interface StudentGradeService {
    ApiResponseStudent<StudentGradeResponse> getGradeByStudentId(Long studentId);
}