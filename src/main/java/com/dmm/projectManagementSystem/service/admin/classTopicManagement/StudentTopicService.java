package com.dmm.projectManagementSystem.service.admin.classTopicManagement;

import com.dmm.projectManagementSystem.dto.studentTopic.CUDStudentTopicRequest;
import com.dmm.projectManagementSystem.dto.studentTopic.StudentTopicResponse;
import com.dmm.projectManagementSystem.model.ClassTopic;

public interface StudentTopicService {
    void addStudentTopic(ClassTopic classTopic, CUDStudentTopicRequest requests) throws Exception;
    void deleteStudentTopic(String studentIdNum) throws Exception;
}
