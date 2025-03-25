package com.dmm.projectManagementSystem.service.admin.topicManagement;

import com.dmm.projectManagementSystem.dto.topic.TopicDetailsResponse;
import com.dmm.projectManagementSystem.dto.topic.TopicListByPageResponse;
import org.springframework.data.util.Pair;

public interface TopicManagementService {
    Pair<String, Boolean> approveGrade(String idNum);
    Pair<String, Boolean> deleteTopic(String idNum);
    TopicListByPageResponse getAllTopic(String name, Long topicSemesterID, Long majorID, int page, int limit);

    TopicDetailsResponse getDetailTopic(String idNum);
}
