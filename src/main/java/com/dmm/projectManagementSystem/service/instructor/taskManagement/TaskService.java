package com.dmm.projectManagementSystem.service.instructor.taskManagement;

import com.dmm.projectManagementSystem.dto.task.CreateTaskDTO;
import com.dmm.projectManagementSystem.model.Task;

public interface TaskService {
    Task createTask(CreateTaskDTO task);
}
