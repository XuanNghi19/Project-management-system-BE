package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "course")
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    static public Course fromCRUDCourse(CRUDCourse crudCourse) {
        return Course.builder()
                .id(crudCourse.getId())
                .name(crudCourse.getName())
                .startTime(crudCourse.getStartTime())
                .endTime(crudCourse.getEndTime())
                .build();
    }
}
