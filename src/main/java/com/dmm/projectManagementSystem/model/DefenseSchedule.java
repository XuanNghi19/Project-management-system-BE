package com.dmm.projectManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private LocalDateTime startTime;
    private String location;
    private String note;
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "councilID")
    private Council council;

    @ManyToOne
    @JoinColumn(name = "topicId")
    private Topic topic;
}

