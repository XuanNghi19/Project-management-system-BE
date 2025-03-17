package com.dmm.projectManagementSystem.service.admin.topicManagement;

import com.dmm.projectManagementSystem.dto.topic.TopicListByPageResponse;
import com.dmm.projectManagementSystem.model.Grade;
import com.dmm.projectManagementSystem.model.Topic;
import com.dmm.projectManagementSystem.repo.CourseRepo;
import com.dmm.projectManagementSystem.repo.GradeRepo;
import com.dmm.projectManagementSystem.repo.MajorRepo;
import com.dmm.projectManagementSystem.repo.TopicRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicManagementServiceImpl implements TopicManagementService{

    final private TopicRepo topicRepo;
    final private GradeRepo gradeRepo;
    final private CourseRepo courseRepo;
    final private MajorRepo majorRepo;

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

        }

        return Pair.of("Not enough points to approve!", false);
    }

    @Override
    public Pair<String, Boolean> deleteTopic(String idNum) {
        return null;
    }

    @Override
    public TopicListByPageResponse getAllTopic(String name, Long courseID, Long majorID, int page, int limit) {
        return null;
    }
}
