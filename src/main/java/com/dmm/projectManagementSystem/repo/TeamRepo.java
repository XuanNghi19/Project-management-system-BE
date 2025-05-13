package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepo extends JpaRepository<Team, Long> {
    boolean existsByTopic(Topic topic);
    Long deleteAllByTopic(Topic topic);

    Optional<Team> findByTopicId(Long topicId);
    Team findByTopic(Topic topic);

    boolean existsByTopicSemester(TopicSemester topicSemester);
    boolean existsByMajor(Major major);
    List<Team> findByTeacher_Id(Long id);
}
