package com.dmm.projectManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "class_topic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ClassTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String className;

    private LocalDateTime startRegistrationimeTime;
    private LocalDateTime endRegistrationimeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_semester_id")
    private TopicSemester topicSemester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

    @OneToMany(mappedBy = "classTopic", cascade = CascadeType.ALL)
    private List<StudentTopic> listStudentTopic;
}
