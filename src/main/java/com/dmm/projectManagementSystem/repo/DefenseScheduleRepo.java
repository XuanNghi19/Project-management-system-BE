package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Council;
import com.dmm.projectManagementSystem.model.DefenseSchedule;
import com.dmm.projectManagementSystem.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DefenseScheduleRepo extends JpaRepository<DefenseSchedule, Long> {
    boolean existsByTopic(Topic topic);
    Long deleteAllByTopic(Topic topic);

    DefenseSchedule findByTopic(Topic topic);

    List<DefenseSchedule> findAllByCouncil(Council council);

    boolean existsByCouncil(Council council);
    void deleteAllByCouncil(Council council);

    Optional<DefenseSchedule> findByTopicId(Long topicId);
}

