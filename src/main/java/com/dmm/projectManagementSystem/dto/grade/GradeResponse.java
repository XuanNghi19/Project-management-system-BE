package com.dmm.projectManagementSystem.dto.grade;

import com.dmm.projectManagementSystem.model.Grade;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class GradeResponse {
    Long id;

    Double progressScore;
    Double reportScore;
    Double reviewScore;
    Double defenseScore;
    Double finalScore;

    static public GradeResponse fromGrade(Grade grade) {
        return GradeResponse.builder()
                .id(grade.getId())
                .progressScore(grade.getProgressScore())
                .reportScore(grade.getReportScore())
                .reviewScore(grade.getReviewScore())
                .defenseScore(grade.getDefenseScore())
                .finalScore(grade.getFinalScore())
                .build();
    }
}
