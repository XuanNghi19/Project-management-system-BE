package com.dmm.projectManagementSystem.dto.group.res;

import com.dmm.projectManagementSystem.dto.user.UserResponse;
import com.dmm.projectManagementSystem.enums.MembershipPosition;
import com.dmm.projectManagementSystem.model.TeamMember;
import com.dmm.projectManagementSystem.model.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserTeamResDTO {
    String idNum;

    String name;

    boolean status;

    @Enumerated(EnumType.STRING)
    MembershipPosition position;

    public static List<UserTeamResDTO> fromUserTeamRes(
            List<TeamMember> teamMembers, Map<Long, User> userMap, boolean status
    ) {
        AtomicBoolean atomicStatus = new AtomicBoolean(status);
        return teamMembers.stream()
                .map((teamMember) -> {
                    User user = userMap.get(teamMember.getStudent().getId());
                    if (!atomicStatus.get() && teamMember.getPosition() == MembershipPosition.LEADER) {
                        atomicStatus.set(true);
                    }
                    return UserTeamResDTO.builder()
                            .idNum(user != null ? user.getIdNum() : "Unknown")
                            .name(user != null ? user.getName() : "Unknown")
                            .position(teamMember.getPosition())
                            .status(atomicStatus.get())
                            .build();
                })
                .toList();
    }
}
