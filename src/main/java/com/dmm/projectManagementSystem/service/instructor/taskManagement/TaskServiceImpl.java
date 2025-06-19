package com.dmm.projectManagementSystem.service.instructor.taskManagement;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.task.CreateTaskDTO;
import com.dmm.projectManagementSystem.exception.InvalidException;
import com.dmm.projectManagementSystem.model.Task;
import com.dmm.projectManagementSystem.model.Topic;
import com.dmm.projectManagementSystem.repo.TaskRepo;
import com.dmm.projectManagementSystem.repo.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private TopicRepo topicRepo;
    @Override
    public ApiResponse<String> createTask(CreateTaskDTO task) {
        if(!topicRepo.existsById(task.getTopicID())) throw new InvalidException("khong ton tai topic");
        Optional<Topic> topic=topicRepo.findById(task.getTopicID());
        Task res=Task.builder()
                .title(task.getTitle())
                .describe(task.getDescribe())
                .comment(task.getComment())
                .deadline(task.getDeadline())
                .status(task.isStatus())
                .topic(topic.get())
                .build();
        taskRepo.save(res);
        return new ApiResponse<>(1000,"Tạo thành công","");
    }
}
