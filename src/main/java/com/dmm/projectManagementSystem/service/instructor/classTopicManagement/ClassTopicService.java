package com.dmm.projectManagementSystem.service.instructor.classTopicManagement;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.classTopic.ClassTopicDTO;
import com.dmm.projectManagementSystem.model.ClassTopic;

import java.util.List;

public interface ClassTopicService {
    ApiResponse<List<ClassTopicDTO>> getListClassTopic(Long id);
}
