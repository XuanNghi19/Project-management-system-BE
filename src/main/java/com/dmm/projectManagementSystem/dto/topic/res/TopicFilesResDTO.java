package com.dmm.projectManagementSystem.dto.topic.res;

import com.dmm.projectManagementSystem.model.FilesUrl;
import com.dmm.projectManagementSystem.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicFilesResDTO {
    private Long topicId;
    private String topicName;
    private String projectStage;
    private List<FileInfoDTO> files;

    public static TopicFilesResDTO from(Topic topic, List<FileInfoDTO> files) {
        return TopicFilesResDTO.builder()
                .topicId(topic.getId())
                .topicName(topic.getName())
                .projectStage(topic.getProjectStage() != null ? topic.getProjectStage().name() : null)
                .files(files)
                .build();
    }
}