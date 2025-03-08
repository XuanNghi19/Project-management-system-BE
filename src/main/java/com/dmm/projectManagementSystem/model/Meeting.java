package com.dmm.projectManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "meeting")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String startTime;
    private String location;
    private String note;

    @ManyToOne
    @JoinColumn(name = "projectID")
    private Project project;
}

