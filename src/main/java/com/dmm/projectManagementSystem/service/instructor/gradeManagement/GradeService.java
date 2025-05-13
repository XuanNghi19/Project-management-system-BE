package com.dmm.projectManagementSystem.service.instructor.gradeManagement;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.grade.UpdateGradeDTO;

public interface GradeService {
    ApiResponse<String> updateGrade(UpdateGradeDTO updateGradeDTO);
}
