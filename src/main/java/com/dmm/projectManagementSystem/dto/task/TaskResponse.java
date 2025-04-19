package com.dmm.projectManagementSystem.dto.task;


import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.model.Task;
import com.dmm.projectManagementSystem.model.Topic;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TaskResponse {

    Long id;
    String title;
    String describe;
    LocalDateTime deadline;
    String comment;
    boolean status;
    ProjectStage projectStage;

    static public TaskResponse fromTask(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .describe(task.getDescribe())
                .deadline(task.getDeadline())
                .comment(task.getComment())
                .status(task.isStatus())
                .projectStage(task.getProjectStage())
                .build();
    }
}
