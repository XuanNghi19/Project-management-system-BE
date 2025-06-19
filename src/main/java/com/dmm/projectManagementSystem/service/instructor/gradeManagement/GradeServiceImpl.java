package com.dmm.projectManagementSystem.service.instructor.gradeManagement;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.grade.UpdateGradeDTO;
import com.dmm.projectManagementSystem.model.Grade;
import com.dmm.projectManagementSystem.repo.GradeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GradeServiceImpl implements GradeService{
    @Autowired
    private GradeRepo gradeRepo;
    @Override
    public ApiResponse<String> updateGrade(UpdateGradeDTO updateGradeDTO) {

        Optional<Grade> grade=gradeRepo.findById(updateGradeDTO.getId());

        switch (updateGradeDTO.getType()) {
            case "PROGRESS":
                grade.get().setProgressScore(updateGradeDTO.getScore());
                break;
            case "REPORT":
                grade.get().setReportScore(updateGradeDTO.getScore());
                break;
            case "DEFENSE":
                grade.get().setDefenseScore(updateGradeDTO.getScore());
                break;
            case "REVIEW":
                grade.get().setReviewScore(updateGradeDTO.getScore());
                break;
            case "FINAL":
                grade.get().setFinalScore(updateGradeDTO.getScore());
                break;
            default:
                throw new IllegalArgumentException("Invalid score type: " + updateGradeDTO.getScore());
        }
        return null;
    }
}
