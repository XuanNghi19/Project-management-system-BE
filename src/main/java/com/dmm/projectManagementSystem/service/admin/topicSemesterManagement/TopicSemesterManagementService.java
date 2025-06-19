package com.dmm.projectManagementSystem.service.admin.topicSemesterManagement;

import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;
import com.dmm.projectManagementSystem.dto.topicSemester.TopicSemesterListByPageResponse;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;
import java.util.List;

public interface TopicSemesterManagementService {
    boolean addTopicSemester(List<CRUDTopicSemester> cTopicSemesterList) throws Exception;
    boolean updateTopicSemester(CRUDTopicSemester uTopicSemester) throws Exception;
    Pair<String, Boolean> deleteTopicSemester(Long id) throws Exception;

    TopicSemesterListByPageResponse getAllTopicSemester(String name, LocalDateTime start, LocalDateTime end, int page, int limit);
}
