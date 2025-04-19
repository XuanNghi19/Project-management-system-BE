package com.dmm.projectManagementSystem.controller.student;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.project.CouncilResDTO;
import com.dmm.projectManagementSystem.dto.project.DefenseScheduleResDTO;
import com.dmm.projectManagementSystem.model.Evaluation;
import com.dmm.projectManagementSystem.model.Meeting;
import com.dmm.projectManagementSystem.model.Task;
import com.dmm.projectManagementSystem.service.student.projectService.ProjectDetailServiceImpl;
import com.dmm.projectManagementSystem.utils.annotation.ApiMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/manage_topic")
public class ProjectController {
    @Autowired
    private ProjectDetailServiceImpl projectDetailServiceImpl;
    @ApiMessageResponse("Lấy lịch bảo về đồ án")
    @GetMapping("/get_defense_schedule/{projectId}")
    public ResponseEntity<ApiResponseStudent<DefenseScheduleResDTO>> getDefenseSchedule (@PathVariable ("projectId") Long projectId) {
        return ResponseEntity.ok(this.projectDetailServiceImpl.handleGetDefenseSchedule(projectId));
    }
    @ApiMessageResponse("Lấy hội đồng tham gia đánh giá đề tài")
    @GetMapping ("/get_council/{projectId}")
    public ResponseEntity<ApiResponseStudent<CouncilResDTO>> getCouncil (@PathVariable Long projectId){
        return ResponseEntity.ok(this.projectDetailServiceImpl.handleGetCouncil(projectId));
    }
    @ApiMessageResponse("Lấy cuộc họp của nhóm đề tài")
    @GetMapping("/get_meeting")
    public ResponseEntity<ApiResponseStudent<List<Meeting>>> getMeeting (@RequestParam Long topicId,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam (defaultValue = "5") int pageSize,
                                                                   @RequestParam (defaultValue = "asc") String sort) {
        return ResponseEntity.ok(this.projectDetailServiceImpl.handleGetMeeting(topicId, page, pageSize, sort));
    }
    @ApiMessageResponse("Lấy đánh giá đề tài từ giảng viên và hội đồng")
    @GetMapping ("/get_evaluations/{projectId}")
    public ResponseEntity<ApiResponseStudent<List<Evaluation>>> getEvaluation (@PathVariable Long projectId){
        ApiResponseStudent<List<Evaluation>> evaluations = this.projectDetailServiceImpl.handleGetEvaluation(projectId);
        return ResponseEntity.ok(evaluations);
    }

    @ApiMessageResponse("Lấy nhiệm vụ được giao của đề tài !")
    @GetMapping("/get_task")
    public ResponseEntity<ApiResponseStudent<List<Task>>> getTask (@RequestParam Long topicId,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam (defaultValue = "5") int pageSize,
                                                             @RequestParam (defaultValue = "asc") String sort) {
        return ResponseEntity.ok(this.projectDetailServiceImpl.handleGetTask(topicId, page, pageSize, sort));
    }


}
