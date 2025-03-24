package com.dmm.projectManagementSystem.controllers.student;

import com.dmm.projectManagementSystem.dto.topic.StudentTopicReq;
import com.dmm.projectManagementSystem.service.student.projectService.TopicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentTopicController {

    @Autowired
    private TopicServiceImpl topicServiceImpl;

    @PostMapping("/register_topic")
    public ResponseEntity<String> registerTopic (@RequestParam Long leaderId, @RequestParam String topicName) {
        this.topicServiceImpl.handleRegisterTopic(leaderId, topicName);
        return ResponseEntity.ok("Đăng ký đề tài thành công !");
    }

    @PutMapping("/update_topic")
    public ResponseEntity<String> updateTopic (@PathVariable ("id") Long topicId) {
        this.topicServiceImpl.handleUpdateTopic(topicId);
        return ResponseEntity.ok("Cập nhật đề tài thành công !");
    }

}
