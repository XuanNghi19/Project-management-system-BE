package com.dmm.projectManagementSystem.service.instructor.classTopicManagement;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.classTopic.ClassTopicDTO;
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
    public ApiResponse<List<ClassTopicDTO>> getListClassTopic(Long id) {
        List<ClassTopic> list=classTopicRepo.findByTeacher_Id(id);
        List<ClassTopicDTO> res=list.stream().map(x->new ClassTopicDTO(x.getId(),x.getClassName(),x.getStartRegistrationTime(),x.getEndRegistrationTime(),x.getTopicSemester(),x.getMajor())).toList();
        return new ApiResponse<>(1000,"thanh cong",res);
    }
}
