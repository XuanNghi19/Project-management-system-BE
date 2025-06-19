package com.dmm.projectManagementSystem.dto.topic;

import lombok.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class StudentTopicReq {
    private String idNum;
    private String name;

}
