package com.dmm.projectManagementSystem.service.instructor.topicManagement;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.topic.UpdateTopicDTO;
import com.dmm.projectManagementSystem.model.Topic;

import java.util.List;

public interface TopicByInstructorService {
    List<Topic> getListTopic(Long id);
    ApiResponse<String> approvalTopic(UpdateTopicDTO updateTopicDTO);
}
