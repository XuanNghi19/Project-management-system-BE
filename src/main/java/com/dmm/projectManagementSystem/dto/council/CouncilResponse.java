package com.dmm.projectManagementSystem.dto.council;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.model.Course;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CouncilResponse {
    Long id;
    String name;
    String location;
    LocalDateTime startTime;
    LocalDateTime endTime;

    CRUDCourse course;
    CRUDDepartment department;
}
