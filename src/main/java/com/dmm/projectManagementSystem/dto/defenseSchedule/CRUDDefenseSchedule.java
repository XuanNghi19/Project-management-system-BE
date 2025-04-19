package com.dmm.projectManagementSystem.dto.defenseSchedule;

import com.dmm.projectManagementSystem.dto.topic.TopicResponse;
import com.dmm.projectManagementSystem.enums.ActionTypes;
import com.dmm.projectManagementSystem.model.DefenseSchedule;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CRUDDefenseSchedule {
    Long id;

    ActionTypes action;

    TopicResponse topic;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String note;

    static public CRUDDefenseSchedule fromDefenseSchedule(DefenseSchedule defenseSchedule) {
        return CRUDDefenseSchedule.builder()
                .id(defenseSchedule.getId())
                .topic(TopicResponse.fromTopic(defenseSchedule.getTopic()))
                .startTime(defenseSchedule.getStartTime())
                .endTime(defenseSchedule.getEndTime())
                .note(defenseSchedule.getNote())
                .build();
    }
}
