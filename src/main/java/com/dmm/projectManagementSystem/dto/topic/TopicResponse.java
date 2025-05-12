package com.dmm.projectManagementSystem.dto.topic;

import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;
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
    private String name;
    private CRUDMajor major;
    private CRUDTopicSemester topicSemester;

    private ProjectStage projectStage;
    private Grade grade;

    static public TopicResponse fromTopic(Topic topic) {
        return TopicResponse.builder()
                .idNum(topic.getIdNum())
                .name(topic.getName())
                .major(CRUDMajor.fromMajor(topic.getMajor()))
                .topicSemester(CRUDTopicSemester.fromTopicSemester(topic.getTopicSemester()))
                .projectStage(topic.getProjectStage())
                .grade(topic.getGrade())
                .build();
    }
}
