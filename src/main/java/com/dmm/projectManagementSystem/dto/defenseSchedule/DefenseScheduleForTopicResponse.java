package com.dmm.projectManagementSystem.dto.defenseSchedule;

import com.dmm.projectManagementSystem.model.DefenseSchedule;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class DefenseScheduleForTopicResponse {
    Long id;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String location;
    String note;

    static public DefenseScheduleForTopicResponse fromDefenseSchedule(DefenseSchedule defenseSchedule) {
        return DefenseScheduleForTopicResponse.builder()
                .id(defenseSchedule.getId())
                .startTime(defenseSchedule.getStartTime())
                .endTime(defenseSchedule.getEndTime())
                .location(defenseSchedule.getLocation())
                .note(defenseSchedule.getNote())
                .build();
    }
}
