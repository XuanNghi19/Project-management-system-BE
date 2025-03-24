package com.dmm.projectManagementSystem.service.instructor.teamManagement;

import com.dmm.projectManagementSystem.model.Team;
import com.dmm.projectManagementSystem.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService{
    @Autowired
    private TeamRepo teamRepo;
    @Override
    public List<Team> getListTeam(Long id) {

        return teamRepo.findByTeacherId(id);
    }
}
