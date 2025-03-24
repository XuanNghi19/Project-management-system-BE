package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Council;
import com.dmm.projectManagementSystem.model.DefenseSchedule;
import com.dmm.projectManagementSystem.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefenseScheduleRepo extends JpaRepository<DefenseSchedule, Long> {
    boolean existsByTopic(Topic topic);
    Long deleteAllByTopic(Topic topic);

    DefenseSchedule findByTopic(Topic topic);

    List<DefenseSchedule> findAllByCouncil(Council council);

    boolean existsByCouncil(Council council);
    Long deleteAllByCouncil(Council council);
}

