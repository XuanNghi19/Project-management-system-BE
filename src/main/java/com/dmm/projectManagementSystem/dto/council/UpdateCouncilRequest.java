package com.dmm.projectManagementSystem.dto.council;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateCouncilRequest {
    Long id;
    String name;
    String fileUrl;
    String location;
    LocalDateTime startTime;
    LocalDateTime endTime;

    Long courseID;
    Long departmentID;
}
