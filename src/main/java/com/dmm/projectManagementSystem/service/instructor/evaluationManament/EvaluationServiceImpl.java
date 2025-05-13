package com.dmm.projectManagementSystem.service.instructor.evaluationManament;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.evaluation.CreateEvaluationDTO;
import com.dmm.projectManagementSystem.model.Evaluation;
import com.dmm.projectManagementSystem.model.Grade;
import com.dmm.projectManagementSystem.model.Topic;
import com.dmm.projectManagementSystem.repo.EvaluationRepo;
import com.dmm.projectManagementSystem.repo.GradeRepo;
import com.dmm.projectManagementSystem.repo.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private GradeRepo gradeRepo;
    @Autowired
    private EvaluationRepo evaluationRepo;
    @Override
    public ApiResponse<String> createEvaluation(CreateEvaluationDTO createEvaluationDTO) {
        Optional<Topic> topic=topicRepo.findById(createEvaluationDTO.getTopicId());
        Optional<Grade> grade=gradeRepo.findById(topic.get().getGrade().getId());
        switch (createEvaluationDTO.getGradeType()) {
            case PROGRESS_SCORE:
                grade.get().setProgressScore(createEvaluationDTO.getGrade());
                break;
            case REPORT_SCORE:
                grade.get().setReportScore(createEvaluationDTO.getGrade());
                break;
            case DEFENSE_SCORE:
                grade.get().setDefenseScore(createEvaluationDTO.getGrade());
                break;
            case REVIEW_SCORE:
                grade.get().setReviewScore(createEvaluationDTO.getGrade());
                break;
            default:
                throw new IllegalArgumentException("Invalid score type: " + createEvaluationDTO.getGrade());
        }
        gradeRepo.save(grade.get());
        evaluationRepo.save(Evaluation.builder()
                .comment(createEvaluationDTO.getComment())
                .dateCommented(createEvaluationDTO.getDateCommented())
                .gradeType(createEvaluationDTO.getGradeType())
                .grade(createEvaluationDTO.getGrade())
                .topic(topic.get())
                .build());



        return new ApiResponse<>(1000,"Đánh giá thành công",null);
    }
}
