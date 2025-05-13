package com.dmm.projectManagementSystem.service.admin.classTopicManagement;

import com.dmm.projectManagementSystem.dto.studentTopic.CUDStudentTopicRequest;
import com.dmm.projectManagementSystem.model.ClassTopic;

public interface StudentTopicManagementService {
    void addStudentTopic(ClassTopic classTopic, CUDStudentTopicRequest requests) throws Exception;
    void deleteStudentTopic(Long studentTopicID) throws Exception;
}
