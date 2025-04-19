package com.dmm.projectManagementSystem.dto.team;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;
import com.dmm.projectManagementSystem.model.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TeamResponse {
    Long id;
    User teacher;
    String groupName;

    CRUDTopicSemester topicSemester;
    CRUDMajor major;

    List<TeamMemberResponse> teamMemberResponseList;

    static public TeamResponse fromGroup(
            Team team,
            TopicSemester topicSemester,
            Major major,
            List<TeamMember> teamMemberList
    ) {
        return TeamResponse.builder()
                .id(team.getId())
                .teacher(team.getTeacher())
                .groupName(team.getGroupName())
                .topicSemester(CRUDTopicSemester.fromTopicSemester(topicSemester))
                .major(CRUDMajor.fromMajor(major))
                .teamMemberResponseList(teamMemberList.stream().map(TeamMemberResponse::fromTeamMember).toList())
                .build();
    }
}
