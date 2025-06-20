package com.dmm.projectManagementSystem.controllers.student;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.topic.res.ReportResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicRegisterResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicFilesResDTO;
import com.dmm.projectManagementSystem.service.student.topicService.TopicServiceImpl;
import com.dmm.projectManagementSystem.utils.annotation.ApiMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${api.prefix}/topic")
public class StudentTopicController {

    @Autowired
    private TopicServiceImpl topicServiceImpl;

    @ApiMessageResponse("Đăng ký đề tài cho nhóm")
    @PostMapping(value = "/register_topic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseStudent<TopicRegisterResDTO>> registerTopic(
            @RequestParam("leaderId") Long leaderId,
            @RequestParam("topicName") String topicName,
            @RequestParam("file") MultipartFile file) {
        System.out.println("==> Đã vào controller registerTopic");
        try {
            return ResponseEntity.ok(this.topicServiceImpl.handleRegisterTopic(leaderId, topicName, file));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tải tệp lên: " + e.getMessage(), e);
        }
    }

    @ApiMessageResponse("Đăng ký đề tài cá nhân")
    @PostMapping(value = "/register_individual_topic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseStudent<TopicRegisterResDTO>> registerIndividualTopic(
            @RequestParam("studentId") Long studentId,
            @RequestParam("topicName") String topicName,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(this.topicServiceImpl.handleRegisterIndividualTopic(studentId, topicName, file));
    }

    @ApiMessageResponse("Cập nhật thông tin cho đề tài")
    @PutMapping(value = "/update_topic")
    public ResponseEntity<ApiResponseStudent<TopicRegisterResDTO>> updateTopic(
            @RequestParam("studentId") Long studentId,
            @RequestParam(value = "topicNameChange", required = false) String topicNameChange) {
        return ResponseEntity.ok(this.topicServiceImpl.handleUpdateTopic(studentId, topicNameChange));
    }

    @ApiMessageResponse("Báo cáo tiến độ đề tài")
    @PutMapping(value = "/report", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseStudent<ReportResDTO>> reportProgress(
            @RequestParam("studentId") Long studentId,
            @RequestParam(value = "file", required = true) MultipartFile file) {
        return ResponseEntity.ok(this.topicServiceImpl.handleAddFilesUrl(studentId, file));
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

    @ApiMessageResponse("Lấy danh sách file và thông tin đề tài")
    @GetMapping("/get_files_of_topic")
    public ResponseEntity<ApiResponseStudent<TopicFilesResDTO>> getFilesOfTopic(
            @RequestParam("studentId") Long studentId) {
        return ResponseEntity.ok(this.topicServiceImpl.getFilesOfTopicByStudentId(studentId));
    }

}
