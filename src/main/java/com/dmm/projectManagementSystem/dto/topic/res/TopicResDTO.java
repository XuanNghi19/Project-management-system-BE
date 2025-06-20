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

    private String groupName;

    private TeacherTeamResDTO teacher;

    // Individual student info
    private Long studentId;
    private String studentName;

    public static TopicResDTO loadFromTopicRes(Topic topic, Team team) {
        TeacherTeamResDTO teacherDTO = null;
        if (team.getTeacher() != null) {
            teacherDTO = TeacherTeamResDTO.builder()
                    .idNum(team.getTeacher().getIdNum())
                    .name(team.getTeacher().getName())
                    .course(team.getTeacher().getCourse())
                    .department(team.getTeacher().getDepartment())
                    .build();
        }
        return TopicResDTO.builder()
                .groupName(team.getGroupName())
                .teacher(teacherDTO)
                .idNum(topic.getIdNum())
                .name(topic.getName())
                .projectStage(topic.getProjectStage())
                .startTime(topic.getStartTime())
                .endTime(topic.getEndTime())
                .startSubmissionDate(topic.getStartSubmissionDate())
                .endSubmissionDate(topic.getEndSubmissionDate())
                .topicSemester(topic.getTopicSemester())
                .grade(topic.getGrade())
                .build();
    }

    public static TopicResDTO loadFromIndividualTopicRes(Topic topic, User student) {
        return TopicResDTO.builder()
                .idNum(topic.getIdNum())
                .name(topic.getName())
                .projectStage(topic.getProjectStage())
                .startTime(topic.getStartTime())
                .endTime(topic.getEndTime())
                .startSubmissionDate(topic.getStartSubmissionDate())
                .endSubmissionDate(topic.getEndSubmissionDate())
                .topicSemester(topic.getTopicSemester())
                .grade(topic.getGrade())
                .studentId(student.getId())
                .studentName(student.getName())
                .build();
    }
}
