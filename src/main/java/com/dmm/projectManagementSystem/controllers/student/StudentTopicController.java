package com.dmm.projectManagementSystem.controllers.student;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.topic.res.ReportResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicRegisterResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicResDTO;
import com.dmm.projectManagementSystem.service.student.topicService.TopicServiceImpl;
import com.dmm.projectManagementSystem.utils.annotation.ApiMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/topic")
public class StudentTopicController {

    @Autowired
    private TopicServiceImpl topicServiceImpl;

    @ApiMessageResponse("Đăng ký đề tài cho nhóm")
    @PostMapping("/register_topic")
    public ResponseEntity<ApiResponseStudent<TopicRegisterResDTO>> registerTopic(@RequestParam Long leaderId,
            @RequestParam String topicName,
            @RequestParam String uri) {
        return ResponseEntity.ok(this.topicServiceImpl.handleRegisterTopic(leaderId, topicName, uri));
    }

    @ApiMessageResponse("Đăng ký đề tài cá nhân")
    @PostMapping("/register_individual_topic")
    public ResponseEntity<ApiResponseStudent<TopicRegisterResDTO>> registerIndividualTopic(@RequestParam Long studentId,
            @RequestParam String topicName,
            @RequestParam String uri) {
        return ResponseEntity.ok(this.topicServiceImpl.handleRegisterIndividualTopic(studentId, topicName, uri));
    }

    @ApiMessageResponse("Cập nhật thông tin cho đề tài")
    @PutMapping("/update_topic")
    public ResponseEntity<ApiResponseStudent<TopicRegisterResDTO>> updateTopic(
            @RequestParam("studentId") Long studentId,
            @RequestParam(value = "topicNameChange", required = false) String topicNameChange,
            @RequestParam(value = "uri", required = false) String uri) {
        return ResponseEntity.ok(this.topicServiceImpl.handleUpdateTopic(studentId, topicNameChange, uri));
    }

    @ApiMessageResponse("Báo cáo tiến độ đề tài")
    @PutMapping("/report")
    public ResponseEntity<ApiResponseStudent<ReportResDTO>> reportProgress(@RequestParam("topicId") Long topicId,
            @RequestParam(value = "uri", required = false) String uri) {
        return ResponseEntity.ok(this.topicServiceImpl.handleAddFilesUrl(topicId, uri));
    }

    @ApiMessageResponse("Lấy thông tin đề tài đã đăng ký")
    @GetMapping("/get_topic")
    public ResponseEntity<ApiResponseStudent<TopicResDTO>> getTopic(
            @RequestParam(value = "studentId") Long studentId) {
        return ResponseEntity.ok(this.topicServiceImpl.handleGetTopic(studentId));
    }

    @ApiMessageResponse("Kiểm tra xem sinh viên đã đăng ký đề tài chưa")
    @GetMapping("/check_registration")
    public ResponseEntity<Boolean> checkTopicRegistration(@RequestParam Long studentId) {
        return ResponseEntity.ok(this.topicServiceImpl.hasRegisteredTopic(studentId));
    }

    @ApiMessageResponse("Kiểm tra và xử lý nhóm 1 người")
    @GetMapping("/check_single_person_team")
    public ResponseEntity<ApiResponseStudent<String>> checkSinglePersonTeam(@RequestParam Long leaderId) {
        return ResponseEntity.ok(this.topicServiceImpl.checkAndHandleSinglePersonTeam(leaderId));
    }

    @ApiMessageResponse("Kiểm tra trạng thái sinh viên và hướng dẫn API phù hợp")
    @GetMapping("/check_status")
    public ResponseEntity<ApiResponseStudent<String>> checkStudentStatus(@RequestParam Long studentId) {
        return ResponseEntity.ok(this.topicServiceImpl.checkStudentStatus(studentId));
    }

}
