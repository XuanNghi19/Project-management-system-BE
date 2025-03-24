package com.dmm.projectManagementSystem.service.instructor.teamManagement;

import com.dmm.projectManagementSystem.model.Team;

import java.util.List;

public interface TeamService {
    List<Team> getListTeam(Long id);
}
