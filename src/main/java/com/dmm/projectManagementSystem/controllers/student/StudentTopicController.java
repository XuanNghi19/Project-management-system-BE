package com.dmm.projectManagementSystem.controllers.student;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.topic.res.ReportResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicRegisterResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicResDTO;
import com.dmm.projectManagementSystem.model.Topic;
import com.dmm.projectManagementSystem.service.student.topicService.TopicServiceImpl;
import com.dmm.projectManagementSystem.utils.annotation.ApiMessageResponse;
import com.google.protobuf.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
public class StudentTopicController {

    @Autowired
    private TopicServiceImpl topicServiceImpl;

    @ApiMessageResponse("Đăng ký đề tài cho nhóm")
    @PostMapping("/register_topic")
    public ResponseEntity<ApiResponseStudent<TopicRegisterResDTO>> registerTopic (@RequestParam Long leaderId,
                                                                                  @RequestParam Long teamId,
                                                                                  @RequestParam String topicName,
                                                                                  @RequestParam String uri) {
        return ResponseEntity.ok( this.topicServiceImpl.handleRegisterTopic(leaderId, teamId, topicName, uri));
    }
    @ApiMessageResponse("Cập nhật thông tin cho đề tài")
    @PutMapping("/update_topic")
    public ResponseEntity<ApiResponseStudent<TopicRegisterResDTO>> updateTopic (@RequestParam("leaderId") Long leaderId,
                                            @RequestParam("topicId") Long topicId,
                                            @RequestParam(value = "topicNameChange", required = false) String topicNameChange,
                                            @RequestParam(value = "uri", required = false) String uri) {
        return ResponseEntity.ok(this.topicServiceImpl.handleUpdateTopic(leaderId, topicId, topicNameChange, uri));
    }
    @ApiMessageResponse("Báo cáo tiến độ đề tài")
    @PutMapping("/report")
    public ResponseEntity<ApiResponseStudent<ReportResDTO>> reportProgress (@RequestParam("topicId") Long topicId,
                                                                            @RequestParam(value = "uri", required = false) String uri) {
        return ResponseEntity.ok(this.topicServiceImpl.handleAddFilesUrl(topicId, uri));
    }

    @ApiMessageResponse("Lấy thông tin đề tài đã đăng ký")
    @GetMapping("/get_topic")
    public ResponseEntity<ApiResponseStudent<TopicResDTO>> getTopic (
                                                                     @RequestParam(value = "topicId") Long topicId) {
        return ResponseEntity.ok(this.topicServiceImpl.handleGetTopic(topicId));
    }


}
