package com.dmm.projectManagementSystem.dto.council;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.defenseSchedule.CRUDDefenseSchedule;
import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CouncilDetailResponse {
    Long id;
    String name;
    String location;
    LocalDateTime startTime;
    LocalDateTime endTime;

    CRUDCourse course;
    CRUDDepartment department;

    List<CRUDDefenseSchedule> defenseScheduleList;
}
