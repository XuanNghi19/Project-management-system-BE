package com.dmm.projectManagementSystem.service.instructor.broadMemberManament;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.boardMember.BoardMemberDTO;
import com.dmm.projectManagementSystem.model.BoardMember;

import java.util.List;

public interface BroadMemberService {
    ApiResponse<List<BoardMemberDTO>> getListBoardMember(Long id);
}
