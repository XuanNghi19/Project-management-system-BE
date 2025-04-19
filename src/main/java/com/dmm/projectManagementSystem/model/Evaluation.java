package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.enums.GradeType;
import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "evaluation")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "date_commented")
    private LocalDateTime dateCommented;

    private Double grade;

    @Column(name = "project_stage")
    @Enumerated(EnumType.STRING)
    private ProjectStage projectStage;

    @ManyToOne (fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "topic_id")
    private Topic topic;
}

