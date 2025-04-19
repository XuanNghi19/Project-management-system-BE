package com.dmm.projectManagementSystem.controller.instructor;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.team.TeamDTO;
import com.dmm.projectManagementSystem.dto.team.UpdateTeam;
import com.dmm.projectManagementSystem.model.Team;
import com.dmm.projectManagementSystem.service.instructor.teamManagement.TeamServiceImpl;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamController {
    @Autowired
    private TeamServiceImpl teamService;

    @GetMapping("/team/{teacherId}")
    public ResponseEntity<ApiResponse<List<TeamDTO>>> getListTeam(@PathVariable("teacherId") Long id){
        return ResponseEntity.ok(teamService.getListTeam(id));
    }
    @PatchMapping("/team/approval")
    public ResponseEntity<ApiResponse<String>> approvalTeam(@RequestBody UpdateTeam updateTeam){
        return ResponseEntity.ok(teamService.updateTeam(updateTeam));
    }
}
