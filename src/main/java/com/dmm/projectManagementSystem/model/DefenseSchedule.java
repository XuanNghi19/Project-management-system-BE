package com.dmm.projectManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "defense_schedule")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DefenseSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String startTime;
    private String location;
    private String note;
    private String endTime;

    @ManyToOne
    @JoinColumn(name = "councilID")
    private Council council;

    @ManyToOne
    @JoinColumn(name = "projectID")
    private Project project;
}

