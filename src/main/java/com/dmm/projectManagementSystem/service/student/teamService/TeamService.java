package com.dmm.projectManagementSystem.service.student.teamService;

import com.dmm.projectManagementSystem.dto.RestResponse;
import com.dmm.projectManagementSystem.dto.group.StudentTeamResDTO;

public interface TeamService {
    public RestResponse<StudentTeamResDTO> handleCreateGroup(Long userID, String groupName);

    public boolean handleRemoveStudentFromGroup();

    public boolean handleDeleteGroup(Long id);
}
