package com.dmm.projectManagementSystem.service.instructor.meetingManagement;

import com.dmm.projectManagementSystem.dto.meeting.CreateMeetingDTO;
import com.dmm.projectManagementSystem.model.Meeting;

public interface MeetingService {
    Meeting createMeeting(CreateMeetingDTO meeting);
}
