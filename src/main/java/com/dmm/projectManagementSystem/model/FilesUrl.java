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

    private String uri;

    @ManyToOne
    @JoinColumn(name = "topicID")
    private Topic topic;

    @Enumerated(EnumType.STRING)
    private ProjectStage projectStage;
}

