package com.dmm.projectManagementSystem.controller.instructor;

import com.dmm.projectManagementSystem.dto.meeting.CreateMeetingDTO;
import com.dmm.projectManagementSystem.model.Meeting;
import com.dmm.projectManagementSystem.repo.MeetingRepo;
import com.dmm.projectManagementSystem.service.instructor.meetingManagement.MeetingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeetingController {
    @Autowired
    private MeetingServiceImpl meetingService;

    @PostMapping("/meeting")
    public ResponseEntity<Meeting> createMeeting(@RequestBody CreateMeetingDTO meeting){
        return ResponseEntity.ok(meetingService.createMeeting(meeting));
    }
}
