package com.dmm.projectManagementSystem.dto.announcement;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnnouncementStudentResDTO {
    private Long id;
    private String title;
    private String content;
    private String datePosted;
    private Long teamId;
    private String teamName;
    private String projectStage;
}