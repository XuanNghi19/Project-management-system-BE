package com.dmm.projectManagementSystem.service.admin.topicSemesterManagement;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;
import com.dmm.projectManagementSystem.dto.topicSemester.TopicSemesterListByPageResponse;
import com.dmm.projectManagementSystem.model.Course;
import com.dmm.projectManagementSystem.model.TopicSemester;
import com.dmm.projectManagementSystem.repo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicSemesterManagementServiceImpl implements TopicSemesterManagementService {

    final private TopicSemesterRepo topicSemesterRepo;

    final private TopicRepo topicRepo;
    final private CouncilRepo councilRepo;
    final private TeamRepo teamRepo;
    final private ClassTopicRepo classTopicRepo;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean addTopicSemester(List<CRUDTopicSemester> cTopicSemesterList) throws Exception {
        try {
            for (var x : cTopicSemesterList) {
                TopicSemester newTopicSemester = TopicSemester.fromCRUDTopicSemester(x);
                newTopicSemester.setId(null);
                topicSemesterRepo.save(newTopicSemester);
            }
            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            throw new Exception(ex);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean updateTopicSemester(CRUDTopicSemester uTopicSemester) throws Exception {
        try {
            topicSemesterRepo.save(TopicSemester.fromCRUDTopicSemester(uTopicSemester));
            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            throw new Exception(ex);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Pair<String, Boolean> deleteTopicSemester(Long id) throws Exception {
        if (topicSemesterRepo.existsById(id)) {
            TopicSemester exTopicSemester = topicSemesterRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Khong tim thay Topic Semester voi id: " + id));

            if (
                    topicRepo.existsByTopicSemester(exTopicSemester)
                    || councilRepo.existsByTopicSemester(exTopicSemester)
                    || teamRepo.existsByTopicSemester(exTopicSemester)
                    || classTopicRepo.existsByTopicSemester(exTopicSemester)
            ) {
                return Pair.of("still exists in the Topic Semester!", false);
            }

            try {
                topicSemesterRepo.deleteById(id);
                return Pair.of("Deleted!", true);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex);
                throw new Exception(ex);
            }
        }
        return Pair.of(String.format("ID doesn't exists: %d", id), false);
    }

    @Override
    public TopicSemesterListByPageResponse getAllTopicSemester(String name, LocalDateTime start, LocalDateTime end, int page, int limit) {
        Page<CRUDTopicSemester> crudTopicSemesters = topicSemesterRepo.findAllTopicSemester(name, start, end, PageRequest.of(page, limit))
                .map(CRUDTopicSemester::fromTopicSemester);

        return TopicSemesterListByPageResponse.fromSplitPage(crudTopicSemesters.getContent(), crudTopicSemesters.getTotalPages(), page, limit);

    }
}
