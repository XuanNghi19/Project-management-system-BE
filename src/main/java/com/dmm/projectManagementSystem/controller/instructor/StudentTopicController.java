package com.dmm.projectManagementSystem.controller.instructor;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.StudentTopicDTO;
import com.dmm.projectManagementSystem.model.StudentTopic;
import com.dmm.projectManagementSystem.service.instructor.studentTopicManagement.StudentTopicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentTopicController {
    @Autowired
    private StudentTopicServiceImpl studentTopicService;

    @GetMapping("/student_topic/{classTopicId}")
    public ResponseEntity<ApiResponse<List<StudentTopicDTO>>> getListStudentTopic(@PathVariable("classTopicId") Long id){
        return ResponseEntity.ok(studentTopicService.getListStudentTopic(id));
    }
}
