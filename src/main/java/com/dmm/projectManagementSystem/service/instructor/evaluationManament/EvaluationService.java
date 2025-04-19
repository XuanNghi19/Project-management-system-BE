package com.dmm.projectManagementSystem.service.instructor.evaluationManament;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.evaluation.CreateEvaluationDTO;

public interface EvaluationService {
    ApiResponse<String> createEvaluation(CreateEvaluationDTO createEvaluationDTO);
}
