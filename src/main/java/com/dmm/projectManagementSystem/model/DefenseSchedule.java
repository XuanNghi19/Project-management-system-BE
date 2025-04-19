package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.dto.defenseSchedule.CRUDDefenseSchedule;
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

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    private String location;

    @Column(columnDefinition = "TEXT")
    private String note;

    @ManyToOne
    @JoinColumn(name = "council_id")
    private Council council;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;


    static public DefenseSchedule fromCRUDDefenseSchedule(
            CRUDDefenseSchedule crudDefenseSchedule,
            Council council,
            Topic topic
    ) {
        return DefenseSchedule.builder()
                .id(crudDefenseSchedule.getId())
                .startTime(crudDefenseSchedule.getStartTime())
                .endTime(crudDefenseSchedule.getEndTime())
                .location(council.getLocation())
                .note(crudDefenseSchedule.getNote())
                .council(council)
                .topic(topic)
                .build();
    }
}

