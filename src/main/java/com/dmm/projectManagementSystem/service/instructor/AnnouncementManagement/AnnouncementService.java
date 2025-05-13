package com.dmm.projectManagementSystem.service.instructor.AnnouncementManagement;

import com.dmm.projectManagementSystem.dto.announcement.CreateAnnouncementDTO;
import com.dmm.projectManagementSystem.dto.task.CreateTaskDTO;
import com.dmm.projectManagementSystem.model.Announcement;

public interface AnnouncementService {
    Announcement createAnnouncement(CreateAnnouncementDTO announcementDTO);
}
