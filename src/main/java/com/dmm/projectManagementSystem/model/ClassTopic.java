package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.dto.classTopic.CreateClassTopicRequest;
import com.dmm.projectManagementSystem.dto.classTopic.UpdateClassTopicRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "class_topic")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClassTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name")
    private String className;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @Column(name = "start_registration_time")
    private LocalDateTime startRegistrationTime;

    @Column(name = "end_registration_time")
    private LocalDateTime endRegistrationTime;

    @ManyToOne
    @JoinColumn(name = "topic_semester_id")
    private TopicSemester topicSemester;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

    static public ClassTopic fromCreateClassTopicRequest(
            CreateClassTopicRequest request,
            User teacher,
            TopicSemester topicSemester,
            Major major
    ) {
        return ClassTopic.builder()
                .className(request.getClassName())
                .teacher(teacher)
                .startRegistrationTime(request.getStartRegistrationTime())
                .endRegistrationTime(request.getEndRegistrationTime())
                .topicSemester(topicSemester)
                .major(major)
                .build();
    }

    static public ClassTopic fromUpdateClassTopicRequest(
            UpdateClassTopicRequest request,
            User teacher,
            TopicSemester topicSemester,
            Major major
    ) {
        return ClassTopic.builder()
                .id(request.getId())
                .className(request.getClassName())
                .teacher(teacher)
                .startRegistrationTime(request.getStartRegistrationTime())
                .endRegistrationTime(request.getEndRegistrationTime())
                .topicSemester(topicSemester)
                .major(major)
                .build();
    }
}
