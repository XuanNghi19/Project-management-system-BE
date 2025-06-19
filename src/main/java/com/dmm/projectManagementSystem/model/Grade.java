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

    @Column(name = "progress_score")
    private Double progressScore;

    @Column(name = "report_score")
    private Double reportScore;

    @Column(name = "review_score")
    private Double reviewScore;

    @Column(name = "defense_score")
    private Double defenseScore;

    @Column(name = "final_score")
    private Double finalScore;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "grade")
    private Topic topic;
}
