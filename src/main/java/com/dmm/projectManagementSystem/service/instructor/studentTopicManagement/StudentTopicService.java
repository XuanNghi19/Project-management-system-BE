package com.dmm.projectManagementSystem.service.instructor.studentTopicManagement;

import com.dmm.projectManagementSystem.model.StudentTopic;

import java.util.List;

public interface StudentTopicService {
    List<StudentTopic> getListStudentTopic(Long id);
}
