package com.dmm.projectManagementSystem.dto.topic;

import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.model.Grade;
import com.dmm.projectManagementSystem.model.Topic;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TopicResponse {
    private String idNum;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String name;
    private LocalDateTime startSubmissionDate;
    private LocalDateTime endSubmissionDate;

    private ProjectStage projectStage;
    private Grade grade;

    static public TopicResponse fromTopic(Topic topic) {
        return TopicResponse.builder()
                .idNum(topic.getIdNum())
                .startTime(topic.getStartTime())
                .endTime(topic.getEndTime())
                .name(topic.getName())
                .startSubmissionDate(topic.getStartSubmissionDate())
                .endSubmissionDate(topic.getEndSubmissionDate())
                .projectStage(topic.getProjectStage())
                .grade(topic.getGrade())
                .build();
    }
}
