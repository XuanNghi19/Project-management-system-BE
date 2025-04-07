package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.enums.ProjectStage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "announcement")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "date_posted")
    private String datePosted;

    @Column(name = "project_stage")
    @Enumerated(EnumType.STRING)
    private ProjectStage projectStage;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}

