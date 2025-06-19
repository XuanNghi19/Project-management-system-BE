package com.dmm.projectManagementSystem.service.instructor.teamManagement;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.team.TeamDTO;
import com.dmm.projectManagementSystem.dto.team.UpdateTeam;

import java.util.List;

public interface TeamByInstructorService {
    ApiResponse<List<TeamDTO>> getListTeam(Long id);
    ApiResponse<String> updateTeam(UpdateTeam updateTeam);
}
