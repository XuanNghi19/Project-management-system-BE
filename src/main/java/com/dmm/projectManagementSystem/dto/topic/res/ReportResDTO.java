package com.dmm.projectManagementSystem.dto.topic.res;


import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.model.FilesUrl;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ReportResDTO {
    private String uri;

    @Enumerated(EnumType.STRING)
    private ProjectStage projectStage;

    public static ReportResDTO fromReportRes (FilesUrl filesUrl) {
        return ReportResDTO.builder()
                .uri(filesUrl.getUri())
                .projectStage(filesUrl.getProjectStage())
                .build();
    }

}
