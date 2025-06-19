package com.dmm.projectManagementSystem.dto.team;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvitationDTO {
    private Long teamId;
    private String teamName;
    private Long leaderId;
    private String leaderName;
}