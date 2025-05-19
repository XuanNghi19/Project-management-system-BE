package com.dmm.projectManagementSystem.service.student.topicService;


import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.topic.res.ReportResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicRegisterResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicResDTO;

public interface TopicService {
//    public boolean handleRegisterTopic(Long leaderId, String topicName);
//    public boolean handleUpdateTopic(Long id);
//    public Topic handleGetTopic(Long id);
//    List<Topic> handleGetAllTopic (Long userId);

    ApiResponseStudent<TopicRegisterResDTO> handleRegisterTopic(Long leaderId, String topicName, String uri);

    ApiResponseStudent<TopicRegisterResDTO> handleUpdateTopic(Long leaderId, Long topicId, String topicNameChange, String uri);

    ApiResponseStudent<ReportResDTO> handleAddFilesUrl (Long topicId, String uri);

    ApiResponseStudent<TopicResDTO> handleGetTopic(Long topicId);
}
