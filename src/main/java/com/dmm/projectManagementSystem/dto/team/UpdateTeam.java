package com.dmm.projectManagementSystem.dto.team;

import com.dmm.projectManagementSystem.enums.TeamStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdateTeam {
    private Long id;
    @Enumerated(EnumType.STRING)
    private TeamStatus status;
}
