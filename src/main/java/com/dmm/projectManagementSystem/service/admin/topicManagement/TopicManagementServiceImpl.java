package com.dmm.projectManagementSystem.service.admin.topicManagement;

import com.dmm.projectManagementSystem.dto.topic.TopicDetailsResponse;
import com.dmm.projectManagementSystem.dto.topic.TopicListByPageResponse;
import com.dmm.projectManagementSystem.dto.topic.TopicResponse;
import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.model.*;
import com.dmm.projectManagementSystem.repo.*;
import com.dmm.projectManagementSystem.service.serviceUtils.FirebaseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicManagementServiceImpl implements TopicManagementService{

    final private FirebaseService firebaseService;

    final private TopicRepo topicRepo;
    final private TopicSemesterRepo topicSemesterRepo;
    final private MajorRepo majorRepo;

    final private TaskRepo taskRepo;
    final private AnnouncementRepo announcementRepo;
    final private FilesUrlRepo filesUrlRepo;
    final private GradeRepo gradeRepo;
    final private EvaluationRepo evaluationRepo;
    final private TeamRepo teamRepo;
    final private MeetingRepo meetingRepo;
    final private DefenseScheduleRepo defenseScheduleRepo;

    final private TeamMemberRepo teamMemberRepo;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Pair<Pair<String, Boolean>, Double> approveGrade(String idNum) throws Exception{
        Topic exTopic = topicRepo.findByIdNum(idNum);
        Grade exGrade = exTopic.getGrade();
        if(
                exTopic.getProjectStage() == ProjectStage.DEFENSE
                && exGrade.getProgressScore() != null
                && exGrade.getReportScore() != null
                && exGrade.getReviewScore() != null
                && exGrade.getDefenseScore() != null
        ) {
            Major major = exTopic.getMajor();
            Double finalScore = exGrade.getProgressScore()*(major.getProgressPercentage()/100.0)
                    + exGrade.getReportScore()*(major.getReportPercentage()/100.0)
                    + exGrade.getReviewScore()*(major.getReviewPercentage()/100.0)
                    + exGrade.getDefenseScore()*(major.getDefensePercentage()/100.0);
            exGrade.setFinalScore(finalScore);
            try {
                gradeRepo.save(exGrade);
                return Pair.of(Pair.of("Approve succeeded!", true), finalScore);
            } catch (Exception e) {
                throw new Exception(e);
            }

        }

        return Pair.of(Pair.of("Not enough points to approve!", false), 0D);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Pair<String, Boolean> deleteTopic(String idNum) throws Exception {
        if(topicRepo.existsByIdNum(idNum)) {
            Topic topic = topicRepo.findByIdNum(idNum);

            if(taskRepo.existsByTopic(topic)) {
                if(taskRepo.deleteAllByTopic(topic) <= 0) {
                    return Pair.of(String.format("cannot delete tasks by topic of idNum: %s", idNum), false);
                }
            }

            if(announcementRepo.existsByTopic(topic)) {
                if (announcementRepo.deleteAllByTopic(topic) <= 0) {
                    return Pair.of(String.format("cannot delete announcements by topic of idNum: %s", idNum), false);
                }
            }

            List<FilesUrl> fileUrls = filesUrlRepo.findAllByTopic(topic);
            if(!fileUrls.isEmpty()) {
                for(var x : fileUrls) {
                    if(!firebaseService.deleteFile(x.getUri())) {
                        System.out.println("firebase service delete file failed!");
                        return Pair.of("firebase service delete file failed", false);
                    }
                }
                if(!filesUrlRepo.deleteAllByTopic(topic)) {
                    return Pair.of(String.format("cannot delete fileUrls by topic of idNum: %s", idNum), false);
                }
            }

            if(evaluationRepo.existsByTopic(topic)) {
                if (evaluationRepo.deleteAllByTopic(topic) <= 0) {
                    return Pair.of(String.format("cannot delete evaluation by topic of idNum: %s", idNum), false);
                }
            }

            if(teamRepo.existsByTopic(topic)) {
                if (teamRepo.deleteAllByTopic(topic) <= 0) {
                    return Pair.of(String.format("cannot delete group by topic of idNum: %s", idNum), false);
                }
            }

            if(meetingRepo.existsByTopic(topic)) {
                if (meetingRepo.deleteAllByTopic(topic) <= 0) {
                    return Pair.of(String.format("cannot delete meeting by topic of idNum: %s", idNum), false);
                }
            }

            if(defenseScheduleRepo.existsByTopic(topic)) {
                if (defenseScheduleRepo.deleteAllByTopic(topic) <= 0) {
                    return Pair.of(String.format("cannot delete defenseSchedule by topic of idNum: %s", idNum), false);
                }
            }

            try {
                if(topicRepo.deleteByIdNum(idNum) > 0) {
                    return Pair.of("Deleted!", true);
                }

                return Pair.of(String.format("Cannot delete this topic, idNum: " + idNum), true);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                throw new Exception(e);
            }
        }
        return Pair.of(String.format("topic with idNum does not exist: %s", idNum), false);
    }

    @Override
    public TopicListByPageResponse getAllTopic(String name, Long topicSemesterID, Long majorID, int page, int limit) {
        Major major = null;
        TopicSemester topicSemester = null;

        if (majorID != null) {
            major = majorRepo.findById(majorID)
                    .orElseThrow(() -> new RuntimeException("Khong tim thay majorId: " + majorID));
        }
        if (topicSemesterID != null) {
            topicSemester = topicSemesterRepo.findById(topicSemesterID)
                    .orElseThrow(() -> new RuntimeException("Khong tim thay courseId: " + topicSemesterID));
        }

        Page<TopicResponse> topicResponsePage = topicRepo.findAllTopic(
                        majorID,
                        topicSemesterID,
                        name,
                        PageRequest.of(page, limit)
                )
                .map(TopicResponse::fromTopic);

        return TopicListByPageResponse.fromSplitPage(topicResponsePage.getContent(), topicResponsePage.getTotalPages(), page, limit);
    }

    @Override
    public TopicDetailsResponse getDetailTopic(String idNum) {
        Topic exTopic = topicRepo.findByIdNum(idNum);

        Team team = teamRepo.findByTopic(exTopic);
        if(team == null) team = new Team();

        List<TeamMember> teamMemberList = teamMemberRepo.findAllByTeam(team);

        List<Task> taskList = taskRepo.findAllByTopic(exTopic);
        List<Announcement> announcementList = announcementRepo.findAllByTopic(exTopic);
        List<FilesUrl> filesUrlList = filesUrlRepo.findAllByTopic(exTopic);
        List<Evaluation> evaluationList = evaluationRepo.findAllByTopic(exTopic);
        List<Meeting> meetingList = meetingRepo.findAllByTopic(exTopic);

        DefenseSchedule defenseSchedule = defenseScheduleRepo.findByTopic(exTopic);
        if(defenseSchedule == null) defenseSchedule = new DefenseSchedule();

        return TopicDetailsResponse.fromTopicAllData(
                exTopic,
                team,
                teamMemberList,
                taskList,
                announcementList,
                filesUrlList,
                evaluationList,
                meetingList,
                defenseSchedule
        );
    }
}
