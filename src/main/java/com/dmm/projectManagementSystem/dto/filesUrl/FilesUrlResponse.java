package com.dmm.projectManagementSystem.dto.filesUrl;

import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.model.FilesUrl;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class FilesUrlResponse {

    Long id;
    String uri;
    ProjectStage projectStage;

    static public FilesUrlResponse fromFileUrl(FilesUrl filesUrl) {
        return FilesUrlResponse.builder()
                .id(filesUrl.getId())
                .uri(filesUrl.getUri())
                .projectStage(filesUrl.getProjectStage())
                .build();
    }
}
