package com.dmm.projectManagementSystem.service.admin.topicManagement;

import com.dmm.projectManagementSystem.dto.topic.TopicDetailsResponse;
import com.dmm.projectManagementSystem.dto.topic.TopicListByPageResponse;
import com.dmm.projectManagementSystem.dto.topic.TopicResponse;
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
    final private CourseRepo courseRepo;
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

    @Transactional
    @Override
    public Pair<String, Boolean> approveGrade(String idNum) {
        Topic exTopic = topicRepo.findByIdNum(idNum);
        Grade exGrade = exTopic.getGrade();
        if(
                exGrade.getProgressScore() != null
                && exGrade.getReportScore() != null
                && exGrade.getReviewScore() != null
                && exGrade.getDefenseScore() != null
        ) {
            Major major = exTopic.getMajor();
            Double finalScore = exGrade.getProgressScore()*major.getProgressPercentage()
                    + exGrade.getReportScore()*major.getReportPercentage()
                    + exGrade.getReviewScore()*major.getReviewPercentage()
                    + exGrade.getDefenseScore()*major.getDefensePercentage();
            exGrade.setFinalScore(finalScore);
            gradeRepo.save(exGrade);
            return Pair.of("Approve succeeded!", true);
        }

        return Pair.of("Not enough points to approve!", false);
    }

    @Transactional
    @Override
    public Pair<String, Boolean> deleteTopic(String idNum) {
        if(topicRepo.existsByIdNum(idNum)) {
            Topic topic = topicRepo.findByIdNum(idNum);

            if(taskRepo.existsByTopic(topic)) {
                if(!taskRepo.deleteAllByTopic(topic)) {
                    return Pair.of(String.format("cannot delete tasks by topic of idNum: %s", idNum), false);
                }
            }

            if(announcementRepo.existsByTopic(topic)) {
                if (!announcementRepo.deleteAllByTopic(topic)) {
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
                if (!evaluationRepo.deleteAllByTopic(topic)) {
                    return Pair.of(String.format("cannot delete evaluation by topic of idNum: %s", idNum), false);
                }
            }

            if(teamRepo.existsByTopic(topic)) {
                if (!teamRepo.deleteAllByTopic(topic)) {
                    return Pair.of(String.format("cannot delete group by topic of idNum: %s", idNum), false);
                }
            }

            if(meetingRepo.existsByTopic(topic)) {
                if (!meetingRepo.deleteAllByTopic(topic)) {
                    return Pair.of(String.format("cannot delete meeting by topic of idNum: %s", idNum), false);
                }
            }

            if(defenseScheduleRepo.existsByTopic(topic)) {
                if (!defenseScheduleRepo.deleteAllByTopic(topic)) {
                    return Pair.of(String.format("cannot delete defenseSchedule by topic of idNum: %s", idNum), false);
                }
            }

            topicRepo.deleteByIdNum(idNum);
            return Pair.of("Deleted!", true);
        }
        return Pair.of(String.format("topic with idNum does not exist: %s", idNum), false);
    }

    @Override
    public TopicListByPageResponse getAllTopic(String name, Long courseID, Long majorID, int page, int limit) {
        Major major = null;
        Course course = null;

        if (majorID != null) {
            major = majorRepo.findById(majorID)
                    .orElseThrow(() -> new RuntimeException("Khong tim thay majorId: " + majorID));
        }
        if (courseID != null) {
            course = courseRepo.findById(courseID)
                    .orElseThrow(() -> new RuntimeException("Khong tim thay courseId: " + courseID));
        }

        Page<TopicResponse> topicResponsePage = topicRepo.findAllTopic(
                        major,
                        course,
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
        List<TeamMember> teamMemberList = teamMemberRepo.findAllByTeam(team);

        List<Task> taskList = taskRepo.findAllByTopic(exTopic);
        List<Announcement> announcementList = announcementRepo.findAllByTopic(exTopic);
        List<FilesUrl> filesUrlList = filesUrlRepo.findAllByTopic(exTopic);
        List<Evaluation> evaluationList = evaluationRepo.findAllByTopic(exTopic);
        List<Meeting> meetingList = meetingRepo.findAllByTopic(exTopic);
        DefenseSchedule defenseSchedule = defenseScheduleRepo.findByTopic(exTopic);

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
