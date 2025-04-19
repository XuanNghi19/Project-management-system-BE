package com.dmm.projectManagementSystem.dto.meeting;

import com.dmm.projectManagementSystem.model.Meeting;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class MeetingResponse {
    Long id;
    String title;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String location;
    String note;

    static public MeetingResponse fromMeeting(Meeting meeting) {
        return MeetingResponse.builder()
                .id(meeting.getId())
                .title(meeting.getTitle())
                .startTime(meeting.getStartTime())
                .endTime(meeting.getEndTime())
                .location(meeting.getLocation())
                .note(meeting.getNote())
                .build();
    }
}
