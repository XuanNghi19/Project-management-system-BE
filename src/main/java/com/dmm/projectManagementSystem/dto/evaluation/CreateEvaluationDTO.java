package com.dmm.projectManagementSystem.dto.evaluation;

import com.dmm.projectManagementSystem.enums.GradeType;
import com.dmm.projectManagementSystem.model.Topic;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateEvaluationDTO {
    private String comment;
    private LocalDateTime dateCommented;
    private String grade;
    @Enumerated(EnumType.STRING)
    private GradeType gradeType;
    private Long topicId;
}
