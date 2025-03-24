package com.dmm.projectManagementSystem.service.instructor.classTopicManagement;

import com.dmm.projectManagementSystem.model.ClassTopic;

import java.util.List;

public interface ClassTopicService {
    List<ClassTopic> getListClassTopic(Long id);
}
