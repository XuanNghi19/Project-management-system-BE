package com.dmm.projectManagementSystem.dto.topic;

import com.dmm.projectManagementSystem.enums.ProjectStage;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UpdateTopicDTO {
    private Long id;
    @Enumerated(EnumType.STRING)
    private ProjectStage projectStage;
}
