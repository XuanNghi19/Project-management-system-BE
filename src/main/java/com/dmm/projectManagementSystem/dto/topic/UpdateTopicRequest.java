package com.dmm.projectManagementSystem.dto.topic;

import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.model.Grade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTopicRequest {
    LocalDateTime startTime;
    LocalDateTime endTime;
    String name;
    LocalDateTime startSubmissionDate;
    LocalDateTime endSubmissionDate;

    ProjectStage projectStage;
    Grade grade;
}
