package com.dmm.projectManagementSystem.service.student.projectService;


import com.dmm.projectManagementSystem.dto.topic.StudentTopicReq;
import com.dmm.projectManagementSystem.model.Topic;

public interface TopicService {
    public boolean handleRegisterTopic(Long leaderId, String topicName);
    public boolean handleUpdateTopic(Long id);
    public Topic handleGetTopic(Long id);
}
