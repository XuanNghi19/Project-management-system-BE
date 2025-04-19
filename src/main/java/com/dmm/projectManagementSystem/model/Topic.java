package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.dto.topic.StudentTopicReq;
import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    private String name;

    @Column(name = "start_submission_date")
    private LocalDateTime startSubmissionDate;

    @Column(name = "end_submission_date")
    private LocalDateTime endSubmissionDate;

    @Column(name = "project_stage")
    @Enumerated(EnumType.STRING)
    private ProjectStage projectStage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "topic_semester_id")
    private TopicSemester topicSemester;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;


    public Topic loadFromStudentTopicReq (StudentTopicReq studentTopicReq){
        return Topic.builder()
                .idNum(studentTopicReq.getIdNum())
                .name(studentTopicReq.getName())
                .build();
    }

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Meeting> meeting;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Evaluation> evaluation;
}

