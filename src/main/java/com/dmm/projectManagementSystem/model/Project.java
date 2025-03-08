package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.enums.ProjectStage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "graduation_project")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idNum;
    private String startTime;
    private String endTime;
    private String name;
    private String startSubmissionDate;
    private String endSubmissionDate;

    @Enumerated(EnumType.STRING)
    private ProjectStage projectStage;

    @ManyToOne
    @JoinColumn(name = "topicID")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "gradeID")
    private Grade grade;
}

