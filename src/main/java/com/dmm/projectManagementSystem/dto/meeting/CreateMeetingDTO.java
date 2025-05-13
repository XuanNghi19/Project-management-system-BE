package com.dmm.projectManagementSystem.dto.meeting;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateMeetingDTO {
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String note;
    private Long topicId;
}
