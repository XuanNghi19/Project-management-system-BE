package com.dmm.projectManagementSystem.dto.council;

import com.dmm.projectManagementSystem.dto.boardMember.CRUDBoardMember;
import com.dmm.projectManagementSystem.dto.defenseSchedule.CRUDDefenseSchedule;
import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;
import com.dmm.projectManagementSystem.model.Council;
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

    CRUDTopicSemester topicSemester;
    CRUDDepartment department;

    List<CRUDDefenseSchedule> defenseScheduleList;

    List<CRUDBoardMember> boardMemberList;

    static public CouncilDetailResponse fromCouncil(
            Council council,
            List<CRUDDefenseSchedule> defenseScheduleList,
            List<CRUDBoardMember> boardMemberList
    ) {
        return CouncilDetailResponse.builder()
                .id(council.getId())
                .name(council.getName())
                .location(council.getLocation())
                .startTime(council.getStartTime())
                .endTime(council.getEndTime())
                .topicSemester(CRUDTopicSemester.fromTopicSemester(council.getTopicSemester()))
                .department(CRUDDepartment.fromDepartment(council.getDepartment()))
                .defenseScheduleList(defenseScheduleList)
                .boardMemberList(boardMemberList)
                .build();
    }
}
