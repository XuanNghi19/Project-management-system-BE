package com.dmm.projectManagementSystem.dto.classTopic;

import com.dmm.projectManagementSystem.model.Course;
import com.dmm.projectManagementSystem.model.Major;
import com.dmm.projectManagementSystem.model.TopicSemester;
import com.dmm.projectManagementSystem.model.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@AllArgsConstructor
@Data
public class ClassTopicDTO {
    private Long id;
    private String name;
    private LocalDateTime startRegistrationimeTime;
    private LocalDateTime endRegistrationimeTime;
    private TopicSemester topicSemester;
    private Major major;
}
