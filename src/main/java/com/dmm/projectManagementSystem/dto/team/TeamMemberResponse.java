package com.dmm.projectManagementSystem.dto.team;

import com.dmm.projectManagementSystem.enums.MembershipPosition;
import com.dmm.projectManagementSystem.model.TeamMember;
import com.dmm.projectManagementSystem.model.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TeamMemberResponse {
    Long id;
    User student;
    MembershipPosition position;

    static public TeamMemberResponse fromTeamMember(TeamMember teamMember) {
        return TeamMemberResponse.builder()
                .id(teamMember.getId())
                .student(teamMember.getStudent())
                .position(teamMember.getPosition())
                .build();
    }
}
