package com.dmm.projectManagementSystem.dto.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
    private Long id;
    private String name;
    private String email;
    private String position; // LEADER / MEMBER
}
