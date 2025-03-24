package com.dmm.projectManagementSystem.service.instructor.studentTopicManagement;

import com.dmm.projectManagementSystem.model.StudentTopic;
import com.dmm.projectManagementSystem.repo.StudentTopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentTopicServiceImpl implements StudentTopicService {
    @Autowired
    private StudentTopicRepo studentTopicRepo;


    @Override
    public List<StudentTopic> getListStudentTopic(Long id) {
        return studentTopicRepo.findByClassTopicId(id);
    }
}
