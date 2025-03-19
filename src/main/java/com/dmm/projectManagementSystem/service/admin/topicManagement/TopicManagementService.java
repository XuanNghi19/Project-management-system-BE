package com.dmm.projectManagementSystem.service.admin.topicManagement;

import com.dmm.projectManagementSystem.dto.topic.CreateTopicRequest;
import com.dmm.projectManagementSystem.dto.topic.TopicListByPageResponse;
import com.dmm.projectManagementSystem.dto.topic.UpdateTopicRequest;
import org.springframework.data.util.Pair;

import java.util.List;

public interface TopicManagementService {
    Pair<String, Boolean> approveGrade(String idNum);
    Pair<String, Boolean> deleteTopic(String idNum);
    TopicListByPageResponse getAllTopic(String name, Long courseID, Long majorID, int page, int limit);

    // getDetail topic
}
