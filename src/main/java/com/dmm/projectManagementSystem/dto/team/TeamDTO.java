package com.dmm.projectManagementSystem.dto.team;

import com.dmm.projectManagementSystem.enums.TeamStatus;
import com.dmm.projectManagementSystem.model.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class TeamDTO {
    private Long id;
    private String groupName;
    private String topicName;
    @Enumerated(EnumType.STRING)
    private TeamStatus status;
    private TopicSemester topicSemester;
    private Major major;
    private Grade grade;
}
