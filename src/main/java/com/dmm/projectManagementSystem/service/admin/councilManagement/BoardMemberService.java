package com.dmm.projectManagementSystem.service.admin.councilManagement;

import com.dmm.projectManagementSystem.dto.boardMember.CRUDBoardMember;
import com.dmm.projectManagementSystem.model.Council;

public interface BoardMemberService {
    void addBoardMember(Council council, CRUDBoardMember request) throws Exception;
    void updateBoardMember(CRUDBoardMember request) throws Exception;
    void deleteBoardMember(Long boardMemberID) throws Exception;
}
