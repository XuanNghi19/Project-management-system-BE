package com.dmm.projectManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grade")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double progressScore;
    private Double reportScore;
    private Double reviewScore;
    private Double defenseScore;
    private Double finalScore;
}
