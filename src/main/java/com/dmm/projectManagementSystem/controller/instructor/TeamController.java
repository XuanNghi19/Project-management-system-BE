package com.dmm.projectManagementSystem.controller.instructor;

import com.dmm.projectManagementSystem.model.Team;
import com.dmm.projectManagementSystem.service.instructor.teamManagement.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamController {
    @Autowired
    private TeamServiceImpl teamService;

    @GetMapping("/team/{teacherId}")
    public ResponseEntity<List<Team>> getListTeam(@PathVariable("teacherId") Long id){
        return ResponseEntity.ok(teamService.getListTeam(id));
    }
}
