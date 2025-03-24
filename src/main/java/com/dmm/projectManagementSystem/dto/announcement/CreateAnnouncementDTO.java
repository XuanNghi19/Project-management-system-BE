package com.dmm.projectManagementSystem.dto.announcement;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CreateAnnouncementDTO {
    private String title;
    private String content;
    private String datePosted;
    private Long topicId;
    private Long teacherId;
}
