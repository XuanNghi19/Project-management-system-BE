package com.dmm.projectManagementSystem.dto.topic;

import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.model.Grade;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTopicRequest {
    LocalDateTime startTime;
    LocalDateTime endTime;
    String name;

    Long courseID;
    Long majorID;
}
