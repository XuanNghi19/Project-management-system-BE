package com.dmm.projectManagementSystem.service.instructor.meetingManagement;

import com.dmm.projectManagementSystem.dto.meeting.CreateMeetingDTO;
import com.dmm.projectManagementSystem.exception.InvalidException;
import com.dmm.projectManagementSystem.model.Meeting;
import com.dmm.projectManagementSystem.model.Topic;
import com.dmm.projectManagementSystem.repo.MeetingRepo;
import com.dmm.projectManagementSystem.repo.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MeetingServiceImpl implements MeetingService{
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private MeetingRepo meetingRepo;
    @Override
    public Meeting createMeeting(CreateMeetingDTO meeting) {
        if(!topicRepo.existsById(meeting.getTopicId())) throw new InvalidException("khong ton tai topic");
        Optional<Topic> topic=topicRepo.findById(meeting.getTopicId());
        Meeting res=Meeting.builder()
                .title(meeting.getTitle())
                .note(meeting.getNote())
                .startTime(meeting.getStartTime())
                .endTime(meeting.getEndTime())
                .location(meeting.getLocation())
                .topic(topic.get())
                .build();

        return meetingRepo.save(res);
    }
}
