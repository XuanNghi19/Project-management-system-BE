package com.dmm.projectManagementSystem.service.admin.classTopicManagement;

import com.dmm.projectManagementSystem.dto.classTopic.ClassTopicDetailResponse;
import com.dmm.projectManagementSystem.dto.classTopic.ClassTopicListByPageResponse;
import com.dmm.projectManagementSystem.dto.classTopic.CreateClassTopicRequest;
import com.dmm.projectManagementSystem.dto.classTopic.UpdateClassTopicRequest;
import org.springframework.data.util.Pair;

public interface ClassTopicManagementService {
    Pair<String, Boolean> addClassTopic(CreateClassTopicRequest classTopic) throws Exception;
    Pair<String, Boolean> updateClassTopic(UpdateClassTopicRequest request) throws Exception;
    Pair<String, Boolean> deleteClassTopic(Long classTopicID) throws Exception;

    ClassTopicListByPageResponse getAllClassTopic(String name, Long topicSemesterID, Long majorID, int page, int limit) throws Exception;
    ClassTopicDetailResponse getClassTopicDetail(Long classTopicID) throws Exception;
}
