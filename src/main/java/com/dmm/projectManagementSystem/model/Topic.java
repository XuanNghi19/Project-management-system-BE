package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "topic")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idNum;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String name;
    private LocalDateTime startSubmissionDate;
    private LocalDateTime endSubmissionDate;

    @Enumerated(EnumType.STRING)
    private ProjectStage projectStage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "topic_semester_id")
    private TopicSemester topicSemester;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;


}

