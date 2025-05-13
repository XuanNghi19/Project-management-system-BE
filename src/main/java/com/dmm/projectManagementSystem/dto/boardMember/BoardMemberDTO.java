package com.dmm.projectManagementSystem.dto.boardMember;

import com.dmm.projectManagementSystem.model.Council;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BoardMemberDTO {
    private Long id;
    private Council council;

}
