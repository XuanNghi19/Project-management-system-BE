package com.dmm.projectManagementSystem.controller.instructor;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.topic.UpdateTopicDTO;
import com.dmm.projectManagementSystem.service.instructor.topicManagement.TopicByInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {
    @Autowired
    private TopicByInstructorService topicService;

    @PatchMapping("/topic/approval")
    public ResponseEntity<ApiResponse<String>> updateTopic(@RequestBody UpdateTopicDTO updateTopicDTO){
        return ResponseEntity.ok(topicService.approvalTopic(updateTopicDTO));
    }
}
