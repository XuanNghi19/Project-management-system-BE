package com.dmm.projectManagementSystem.dto.topic;

import com.dmm.projectManagementSystem.dto.announcement.AnnouncementResponse;
import com.dmm.projectManagementSystem.dto.defenseSchedule.DefenseScheduleForTopicResponse;
import com.dmm.projectManagementSystem.dto.evaluation.EvaluationResponse;
import com.dmm.projectManagementSystem.dto.filesUrl.FilesUrlResponse;
import com.dmm.projectManagementSystem.dto.grade.GradeResponse;
import com.dmm.projectManagementSystem.dto.team.TeamResponse;
import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.dmm.projectManagementSystem.dto.meeting.MeetingResponse;
import com.dmm.projectManagementSystem.dto.task.TaskResponse;
import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;
import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.model.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TopicDetailsResponse {
    
    String idNum;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String name;
    LocalDateTime startSubmissionDate;
    LocalDateTime endSubmissionDate;

    ProjectStage projectStage;
    GradeResponse grade;
    CRUDTopicSemester topicSemester;
    CRUDMajor major;

    TeamResponse team;

    List<TaskResponse> taskList;
    List<AnnouncementResponse> announcementList;
    List<FilesUrlResponse> filesUrlList;
    List<EvaluationResponse> evaluationList;
    List<MeetingResponse> meetingList;
    DefenseScheduleForTopicResponse defenseSchedule;

    static public TopicDetailsResponse fromTopicAllData(
            Topic topic,
            Team team,
            List<TeamMember> teamMemberList,
            List<Task> taskList,
            List<Announcement> announcementList,
            List<FilesUrl> filesUrlList,
            List<Evaluation> evaluationList,
            List<Meeting> meetingList,
            DefenseSchedule defenseSchedule
    ) {
        return TopicDetailsResponse.builder()
                .idNum(topic.getIdNum())
                .startTime(topic.getStartTime())
                .endTime(topic.getEndTime())
                .name(topic.getName())
                .startSubmissionDate(topic.getStartSubmissionDate())
                .endSubmissionDate(topic.getEndSubmissionDate())
                .projectStage(topic.getProjectStage())
                .grade(GradeResponse.fromGrade(topic.getGrade()))
                .topicSemester(CRUDTopicSemester.fromTopicSemester(topic.getTopicSemester()))
                .major(CRUDMajor.fromMajor(topic.getMajor()))
                .team(TeamResponse.fromGroup(
                        team,
                        topic.getTopicSemester(),
                        topic.getMajor(),
                        teamMemberList
                ))
                .taskList(taskList.stream().map(TaskResponse::fromTask).toList())
                .announcementList(announcementList.stream().map(AnnouncementResponse::fromAnnouncement).toList())
                .filesUrlList(filesUrlList.stream().map(FilesUrlResponse::fromFileUrl).toList())
                .evaluationList(evaluationList.stream().map(EvaluationResponse::fromEvaluation).toList())
                .meetingList(meetingList.stream().map(MeetingResponse::fromMeeting).toList())
                .defenseSchedule(DefenseScheduleForTopicResponse.fromDefenseSchedule(defenseSchedule))
                .build();
    }
}
