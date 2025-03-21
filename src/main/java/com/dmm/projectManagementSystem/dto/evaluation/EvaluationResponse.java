package com.dmm.projectManagementSystem.dto.evaluation;

import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.model.Evaluation;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class EvaluationResponse {

    Long id;
    String comment;
    LocalDateTime dateCommented;
    Double grade;
    ProjectStage projectStage;

    static public EvaluationResponse fromEvaluation(Evaluation evaluation) {
        return EvaluationResponse.builder()
                .id(evaluation.getId())
                .comment(evaluation.getComment())
                .dateCommented(evaluation.getDateCommented())
                .grade(evaluation.getGrade())
                .projectStage(evaluation.getProjectStage())
                .build();
    }
}
