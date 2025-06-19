package com.dmm.projectManagementSystem.controller.instructor;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.task.CreateTaskDTO;
import com.dmm.projectManagementSystem.model.Task;
import com.dmm.projectManagementSystem.service.instructor.taskManagement.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    @Autowired
    private TaskServiceImpl taskService;

    @PostMapping("/task")
    public ResponseEntity<ApiResponse<String>> createTask(@RequestBody CreateTaskDTO task){
        return ResponseEntity.ok(taskService.createTask(task));
    }
}
