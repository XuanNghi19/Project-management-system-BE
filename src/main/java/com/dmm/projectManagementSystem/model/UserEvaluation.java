package com.dmm.projectManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_evaluation")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacherID")
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "evaluationID")
    private Evaluation evaluation;
}
