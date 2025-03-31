package com.dmm.projectManagementSystem.controllers.student;

import com.dmm.projectManagementSystem.dto.RestResponse;
import com.dmm.projectManagementSystem.dto.project.CouncilResDTO;
import com.dmm.projectManagementSystem.dto.project.DefenseScheduleResDTO;
import com.dmm.projectManagementSystem.model.Evaluation;
import com.dmm.projectManagementSystem.model.Meeting;
import com.dmm.projectManagementSystem.service.student.projectService.ProjectDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectDetailServiceImpl projectDetailServiceImpl;
    @GetMapping("/get_defense_schedule/{projectId}")
    public ResponseEntity<DefenseScheduleResDTO> getDefenseSchedule (@PathVariable ("projectId") Long projectId) {
        return ResponseEntity.ok(this.projectDetailServiceImpl.handleGetDefenseSchedule(projectId));
    }

    @GetMapping ("/get_council/{projectId}")
    public ResponseEntity<CouncilResDTO> getCouncil (@PathVariable Long projectId){
        return ResponseEntity.ok(this.projectDetailServiceImpl.handleGetCouncil(projectId));
    }

    @GetMapping("/get_meeting")
    public ResponseEntity<RestResponse<List<Meeting>>> getMeeting (@RequestParam Long topicId,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam (defaultValue = "5") int pageSize,
                                                                   @RequestParam (defaultValue = "asc") String sort) {
        return ResponseEntity.ok(this.projectDetailServiceImpl.handleGetMeeting(topicId, page, pageSize, sort));
    }

    @GetMapping ("/get_evaluations/{projectId}")
    public ResponseEntity<RestResponse<List<Evaluation>>> getEvaluation (@PathVariable Long projectId){
        RestResponse<List<Evaluation>> evaluations = this.projectDetailServiceImpl.handleGetEvaluation(projectId);
        return ResponseEntity.ok(evaluations);
    }


}
