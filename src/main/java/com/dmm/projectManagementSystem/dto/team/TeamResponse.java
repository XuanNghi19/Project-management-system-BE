package com.dmm.projectManagementSystem.dto.team;

import com.dmm.projectManagementSystem.model.Team;
import com.dmm.projectManagementSystem.model.TeamMember;
import com.dmm.projectManagementSystem.model.User;
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

    List<TeamMemberResponse> teamMemberResponseList;

    static public TeamResponse fromGroup(
            Team team,
            List<TeamMember> teamMemberList
    ) {
        return TeamResponse.builder()
                .id(team.getId())
                .teacher(team.getTeacher())
                .groupName(team.getTeamName())
                .teamMemberResponseList(teamMemberList.stream().map(TeamMemberResponse::fromTeamMember).toList())
                .build();
    }
}
