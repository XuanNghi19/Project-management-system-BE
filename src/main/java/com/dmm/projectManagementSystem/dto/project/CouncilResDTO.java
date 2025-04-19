package com.dmm.projectManagementSystem.dto.project;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class CouncilResDTO {
    private String name;
    private String fileUrl;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
