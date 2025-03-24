package com.dmm.projectManagementSystem.dto.defenseSchedule;

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
    String topicIdNum;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String note;
}
