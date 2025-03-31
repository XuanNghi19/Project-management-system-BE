package com.dmm.projectManagementSystem.dto.project;

import com.dmm.projectManagementSystem.dto.Metadata;
import lombok.*;

import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeetingResDTO {
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String note;
}
