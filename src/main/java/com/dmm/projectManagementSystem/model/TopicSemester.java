package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "topic_semester")
public class TopicSemester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    static public TopicSemester fromCRUDTopicSemester(CRUDTopicSemester crudTopicSemester) {
        return TopicSemester.builder()
                .id(crudTopicSemester.getId())
                .name(crudTopicSemester.getName())
                .startTime(crudTopicSemester.getStartTime())
                .endTime(crudTopicSemester.getEndTime())
                .build();
    }
}
