package com.dmm.projectManagementSystem.dto.grade;

import com.dmm.projectManagementSystem.model.Grade;
import com.dmm.projectManagementSystem.model.Topic;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentGradeResponse {
    private Long topicId;
    private String topicName;
    private String topicIdNum;
    private Long gradeId;
    private Double progressScore;
    private Double reportScore;
    private Double reviewScore;
    private Double defenseScore;
    private Double finalScore;

    public static StudentGradeResponse fromTopicAndGrade(Topic topic, Grade grade) {
        return StudentGradeResponse.builder()
                .topicId(topic.getId())
                .topicName(topic.getName())
                .topicIdNum(topic.getIdNum())
                .gradeId(grade != null ? grade.getId() : null)
                .progressScore(grade != null ? grade.getProgressScore() : null)
                .reportScore(grade != null ? grade.getReportScore() : null)
                .reviewScore(grade != null ? grade.getReviewScore() : null)
                .defenseScore(grade != null ? grade.getDefenseScore() : null)
                .finalScore(grade != null ? grade.getFinalScore() : null)
                .build();
    }
}