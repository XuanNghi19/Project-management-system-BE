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
public class DefenseScheduleResponse {
    Long id;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String location;
    String note;

    static public DefenseScheduleResponse fromDefenseSchedule(DefenseSchedule defenseSchedule) {
        return DefenseScheduleResponse.builder()
                .id(defenseSchedule.getId())
                .startTime(defenseSchedule.getStartTime())
                .endTime(defenseSchedule.getEndTime())
                .location(defenseSchedule.getLocation())
                .note(defenseSchedule.getNote())
                .build();
    }
}
