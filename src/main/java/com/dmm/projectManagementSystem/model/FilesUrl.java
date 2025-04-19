package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.enums.ProjectStage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files_url")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FilesUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String uri;

    @Column(name = "project_stage")
    @Enumerated(EnumType.STRING)
    private ProjectStage projectStage;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
}

