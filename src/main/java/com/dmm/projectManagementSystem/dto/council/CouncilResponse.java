package com.dmm.projectManagementSystem.dto.council;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;
import com.dmm.projectManagementSystem.model.Council;
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

    CRUDTopicSemester topicSemester;
    CRUDDepartment department;

    static public CouncilResponse fromCouncil(Council council){
        return CouncilResponse.builder()
                .id(council.getId())
                .name(council.getName())
                .location(council.getLocation())
                .startTime(council.getStartTime())
                .endTime(council.getEndTime())
                .topicSemester(CRUDTopicSemester.fromTopicSemester(council.getTopicSemester()))
                .department(CRUDDepartment.fromDepartment(council.getDepartment()))
                .build();
    }
}
