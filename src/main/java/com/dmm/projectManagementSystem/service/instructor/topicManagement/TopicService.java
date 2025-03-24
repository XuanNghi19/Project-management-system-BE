package com.dmm.projectManagementSystem.service.instructor.topicManagement;

import com.dmm.projectManagementSystem.model.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> getListTopic(Long id);
}
