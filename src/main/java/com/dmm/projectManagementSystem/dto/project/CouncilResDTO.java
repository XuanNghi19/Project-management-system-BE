package com.dmm.projectManagementSystem.dto.project;

import com.dmm.projectManagementSystem.model.TopicSemester;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class CouncilResDTO {
    private String name;
    private String fileUrl;
    private String location;
    private String departmentName;
    private TopicSemester topicSemester;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
