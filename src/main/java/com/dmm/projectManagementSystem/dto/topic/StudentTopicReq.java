package com.dmm.projectManagementSystem.dto.topic;

import lombok.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class StudentTopicReq {
    private String idNum;
    private String name;

}
