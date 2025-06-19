package com.dmm.projectManagementSystem.dto.filesUrl;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilesUrlDTO {
    private Long id;
    private String uri;
    private String topicName;
}
