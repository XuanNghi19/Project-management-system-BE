package com.dmm.projectManagementSystem.controller.instructor;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.classTopic.ClassTopicDTO;
import com.dmm.projectManagementSystem.model.ClassTopic;
import com.dmm.projectManagementSystem.service.instructor.classTopicManagement.ClassTopicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClassTopicController {
    @Autowired
    private ClassTopicServiceImpl classTopicService;
    @GetMapping("/class_topic/{teacherId}")
    public ResponseEntity<ApiResponse<List<ClassTopicDTO>>> getListClassTopic(@PathVariable("teacherId") Long id){

        return ResponseEntity.ok(classTopicService.getListClassTopic(id));
    }


}
