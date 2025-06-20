package com.dmm.projectManagementSystem.dto.topic.res;

import com.dmm.projectManagementSystem.dto.group.res.TeacherTeamResDTO;
import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.model.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicRegisterResDTO {
    private String name;
    private ProjectStage projectStage;

    // Simplified FilesUrl info
    private Long filesUrlId;
    private String uri;

    // Simplified TopicSemester info
    private Long topicSemesterId;
    private String topicSemesterName;

    // Simplified Team info (for team topics)
    private Long teamId;
    private String groupName;
    private TeacherTeamResDTO teacher;

    // Individual student info (for individual topics)
    private Long studentId;
    private String studentName;

    public static TopicRegisterResDTO fromTopicRes(Topic topic, FilesUrl filesUrl, Team team) {
        return TopicRegisterResDTO.builder()
                .name(topic.getName())
                .projectStage(topic.getProjectStage())
                .filesUrlId(filesUrl.getId())
                .uri(filesUrl.getUri())
                .topicSemesterId(topic.getTopicSemester().getId())
                .topicSemesterName(topic.getTopicSemester().getName())
                .teamId(team.getId())
                .groupName(team.getGroupName())
                .teacher(team.getTeacher() != null ? TeacherTeamResDTO.loadFromTeacherRes(team.getTeacher()) : null)
                .build();
    }

    public static TopicRegisterResDTO fromIndividualTopicRes(Topic topic, FilesUrl filesUrl, User student) {
        return TopicRegisterResDTO.builder()
                .name(topic.getName())
                .projectStage(topic.getProjectStage())
                .filesUrlId(filesUrl.getId())
                .uri(filesUrl.getUri())
                .topicSemesterId(topic.getTopicSemester().getId())
                .topicSemesterName(topic.getTopicSemester().getName())
                .studentId(student.getId())
                .studentName(student.getName())
                .build();
    }

    public static TopicRegisterResDTO fromTopicResWithoutTeam(Topic topic, FilesUrl filesUrl) {
        return TopicRegisterResDTO.builder()
                .name(topic.getName())
                .projectStage(topic.getProjectStage())
                .filesUrlId(filesUrl != null ? filesUrl.getId() : null)
                .uri(filesUrl != null ? filesUrl.getUri() : null)
                .topicSemesterId(topic.getTopicSemester() != null ? topic.getTopicSemester().getId() : null)
                .topicSemesterName(topic.getTopicSemester() != null ? topic.getTopicSemester().getName() : null)
                .teamId(topic.getTeam() != null ? topic.getTeam().getId() : null)
                .groupName(topic.getTeam() != null ? topic.getTeam().getGroupName() : null)
                .teacher(topic.getTeam() != null && topic.getTeam().getTeacher() != null
                        ? TeacherTeamResDTO.loadFromTeacherRes(topic.getTeam().getTeacher())
                        : null)
                .studentId(topic.getIndividualStudent() != null ? topic.getIndividualStudent().getId() : null)
                .studentName(topic.getIndividualStudent() != null ? topic.getIndividualStudent().getName() : null)
                .build();
    }
}
