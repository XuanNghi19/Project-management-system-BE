package com.dmm.projectManagementSystem.service.admin.topicManagement;

import com.dmm.projectManagementSystem.dto.topic.TopicDetailsResponse;
import com.dmm.projectManagementSystem.dto.topic.TopicListByPageResponse;
import com.dmm.projectManagementSystem.enums.ProjectStage;
import org.springframework.data.util.Pair;

public interface TopicManagementService {
    Pair<Pair<String, Boolean>, Double> approveGrade(String idNum) throws Exception;
    Pair<String, Boolean> deleteTopic(String idNum) throws Exception;
    TopicListByPageResponse getAllTopic(String name, Long topicSemesterID, Long majorID, ProjectStage projectStage, int page, int limit);

    TopicDetailsResponse getDetailTopic(String idNum);
}
