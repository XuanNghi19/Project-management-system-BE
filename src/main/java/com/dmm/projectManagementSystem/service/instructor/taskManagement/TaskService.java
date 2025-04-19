package com.dmm.projectManagementSystem.service.instructor.taskManagement;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.task.CreateTaskDTO;
import com.dmm.projectManagementSystem.model.Task;

public interface TaskService {
    ApiResponse<String> createTask(CreateTaskDTO task);
}
