package com.dmm.projectManagementSystem.model;

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
    @JoinColumn(name = "projectID")
    private Project project;
}

