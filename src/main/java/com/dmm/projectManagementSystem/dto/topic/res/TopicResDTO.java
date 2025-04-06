package com.dmm.projectManagementSystem.dto.topic.res;

import com.dmm.projectManagementSystem.dto.group.res.TeacherTeamResDTO;
import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.model.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicResDTO {
    private String idNum;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String name;
    private LocalDateTime startSubmissionDate;
    private LocalDateTime endSubmissionDate;

    @Enumerated(EnumType.STRING)
    private ProjectStage projectStage;

    private Grade grade;

    private TopicSemester topicSemester;

    private List<Meeting> meeting;

    private List<Evaluation> evaluation;

    private String groupName;

    private TeacherTeamResDTO teacher;

//    List<TeamMember> listStudent;

    public static TopicResDTO loadFromTopicRes (Topic topic, Team team) {

        return TopicResDTO.builder()
                .groupName(team.getGroupName())
                .teacher(TeacherTeamResDTO.loadFromTeacherRes(team.getTeacher()))
                .idNum(topic.getIdNum())
                .name(topic.getName())
                .projectStage(topic.getProjectStage())
                .startTime(topic.getStartTime())
                .endTime(topic.getEndTime())
                .startSubmissionDate(topic.getStartSubmissionDate())
                .endSubmissionDate(topic.getEndSubmissionDate())
                .topicSemester(topic.getTopicSemester())
                .meeting(topic.getMeeting())
                .evaluation(topic.getEvaluation())
                .grade(topic.getGrade())
                .build();
    }
}
