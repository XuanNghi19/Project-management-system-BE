package com.dmm.projectManagementSystem.dto.announcement;

import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.model.Announcement;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AnnouncementResponse {
    Long id;
    String title;
    String content;
    String datePosted;
    ProjectStage projectStage;

    static public AnnouncementResponse fromAnnouncement(Announcement announcement) {
        return AnnouncementResponse.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .datePosted(announcement.getDatePosted())
                .projectStage(announcement.getProjectStage())
                .build();
    }
}
