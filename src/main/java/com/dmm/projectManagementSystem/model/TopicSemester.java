package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "topic_semester")
@Entity
public class TopicSemester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
