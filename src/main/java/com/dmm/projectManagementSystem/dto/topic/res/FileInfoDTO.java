package com.dmm.projectManagementSystem.dto.topic.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoDTO {
    private Long id;
    private String uri;
    private String projectStage;
    private String submittedByName;
}