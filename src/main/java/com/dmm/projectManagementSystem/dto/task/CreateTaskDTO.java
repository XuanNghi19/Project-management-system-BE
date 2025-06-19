package com.dmm.projectManagementSystem.dto.task;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateTaskDTO {
    private String title;
    private String describe;
    private LocalDateTime deadline;
    private String comment;
    private boolean status;
    private Long topicID;
}
