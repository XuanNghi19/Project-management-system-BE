package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.enums.GradeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String comment;
    private String dateCommented;
    private String grade;

    @Enumerated(EnumType.STRING)
    private GradeType gradeType;

    @ManyToOne
    @JoinColumn(name = "projectID")
    private Project project;
}

