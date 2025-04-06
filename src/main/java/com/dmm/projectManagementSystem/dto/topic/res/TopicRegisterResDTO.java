package com.dmm.projectManagementSystem.dto.topic.res;


import com.dmm.projectManagementSystem.model.FilesUrl;
import com.dmm.projectManagementSystem.model.Team;
import com.dmm.projectManagementSystem.model.Topic;
import com.dmm.projectManagementSystem.model.TopicSemester;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TopicRegisterResDTO {
    String name;
    FilesUrl filesUrl;
    TopicSemester topicSemester;
    Team team;

    public static TopicRegisterResDTO fromTopicRes(Topic topic, FilesUrl filesUrl, Team team) {
        return TopicRegisterResDTO
                .builder()
                .name(topic.getName())
                .filesUrl(filesUrl)
                .team(team)
                .topicSemester(topic.getTopicSemester())
                .build();
    }

    public static TopicRegisterResDTO fromTopicResWithoutTeam(Topic topic, FilesUrl filesUrl) {
        return TopicRegisterResDTO
                .builder()
                .name(topic.getName())
                .filesUrl(filesUrl)
                .topicSemester(topic.getTopicSemester())
                .build();
    }
}
