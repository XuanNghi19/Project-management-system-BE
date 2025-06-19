package com.dmm.projectManagementSystem.service.instructor.studentTopicManagement;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.StudentTopicDTO;
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
    public ApiResponse<List<StudentTopicDTO>> getListStudentTopic(Long id) {
        List<StudentTopic> topicList= studentTopicRepo.findByClassTopicId(id);
        List<StudentTopicDTO> res=topicList.stream().map(x->new StudentTopicDTO(x.getId(),x.getStudent().getName(),x.getStudent().getDob(),x.isStatus())).toList();
        return new ApiResponse<>(1000,"thanh cong",res);
    }
}
