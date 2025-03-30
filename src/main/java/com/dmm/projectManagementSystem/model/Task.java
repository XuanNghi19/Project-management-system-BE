package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.enums.ProjectStage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String describe;

    private LocalDateTime deadline;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private boolean status;

    @Column(name = "project_stage")
    @Enumerated(EnumType.STRING)
    private ProjectStage projectStage;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
}

