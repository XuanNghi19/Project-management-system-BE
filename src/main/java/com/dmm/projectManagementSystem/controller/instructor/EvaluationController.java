package com.dmm.projectManagementSystem.controller.instructor;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.evaluation.CreateEvaluationDTO;
import com.dmm.projectManagementSystem.service.instructor.evaluationManament.EvaluationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EvaluationController {
    @Autowired
    private EvaluationServiceImpl evaluationService;
    @PostMapping("/evaluation")
    public ResponseEntity<ApiResponse<String>> createEvaluation(@RequestBody CreateEvaluationDTO createEvaluationDTO){
        return ResponseEntity.ok(evaluationService.createEvaluation(createEvaluationDTO));
    }
}
