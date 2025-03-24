package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

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

    private LocalDateTime startTime;

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
