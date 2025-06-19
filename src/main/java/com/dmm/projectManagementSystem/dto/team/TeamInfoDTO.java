package com.dmm.projectManagementSystem.dto.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.dmm.projectManagementSystem.enums.TeamStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamInfoDTO {
    private Long teamId;
    private String teamName;
    private List<MemberDTO> members;
    private TeamStatus status;
}
