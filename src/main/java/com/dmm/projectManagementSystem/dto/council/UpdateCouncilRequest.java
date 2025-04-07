package com.dmm.projectManagementSystem.dto.council;

import com.dmm.projectManagementSystem.dto.boardMember.CRUDBoardMember;
import com.dmm.projectManagementSystem.dto.defenseSchedule.CRUDDefenseSchedule;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateCouncilRequest {
    Long id;
    String name;
    String fileUrl;
    String location;
    LocalDateTime startTime;
    LocalDateTime endTime;

    Long topicSemesterID;
    Long departmentID;

    List<CRUDDefenseSchedule> scheduleRequests;

    List<CRUDBoardMember> boardMemberList;
}
