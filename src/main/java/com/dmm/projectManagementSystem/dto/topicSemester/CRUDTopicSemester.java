package com.dmm.projectManagementSystem.dto.topicSemester;

import com.dmm.projectManagementSystem.model.TopicSemester;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CRUDTopicSemester {
    Long id;
    String name;
    LocalDateTime startTime;
    LocalDateTime endTime;

    static public CRUDTopicSemester fromTopicSemester(TopicSemester topicSemester) {
        return CRUDTopicSemester.builder()
                .id(topicSemester.getId())
                .name(topicSemester.getName())
                .startTime(topicSemester.getStartTime())
                .endTime(topicSemester.getEndTime())
                .build();
    }
}
