package com.dmm.projectManagementSystem.dto.course;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.model.Course;
import com.dmm.projectManagementSystem.model.Department;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CRUDCourse {
    Long id;
    String name;
    LocalDateTime startTime;
    LocalDateTime endTime;

    static public CRUDCourse fromCourse(Course course) {
        return CRUDCourse.builder()
                .id(course.getId())
                .name(course.getName())
                .startTime(course.getStartTime())
                .endTime(course.getEndTime())
                .build();
    }
}
