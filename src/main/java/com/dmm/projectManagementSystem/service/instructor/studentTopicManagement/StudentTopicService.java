package com.dmm.projectManagementSystem.service.instructor.studentTopicManagement;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.StudentTopicDTO;
import com.dmm.projectManagementSystem.model.StudentTopic;

import java.util.List;

public interface StudentTopicService {
    ApiResponse<List<StudentTopicDTO>> getListStudentTopic(Long id);
}
