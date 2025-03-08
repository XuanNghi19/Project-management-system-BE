package com.dmm.projectManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProjectUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "projectID")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;
}

