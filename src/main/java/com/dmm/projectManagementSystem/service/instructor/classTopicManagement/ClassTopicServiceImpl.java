package com.dmm.projectManagementSystem.service.instructor.classTopicManagement;

import com.dmm.projectManagementSystem.model.ClassTopic;
import com.dmm.projectManagementSystem.repo.ClassTopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassTopicServiceImpl implements ClassTopicService {
    @Autowired
    private ClassTopicRepo classTopicRepo;
    @Override
    public List<ClassTopic> getListClassTopic(Long id) {

        return classTopicRepo.findByTeacherId(id);
    }
}
