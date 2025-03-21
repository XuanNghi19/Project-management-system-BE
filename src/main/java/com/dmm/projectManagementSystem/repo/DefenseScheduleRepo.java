package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.DefenseSchedule;
import com.dmm.projectManagementSystem.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefenseScheduleRepo extends JpaRepository<DefenseSchedule, Long> {
    boolean existsByTopic(Topic topic);
    boolean deleteAllByTopic(Topic topic);

    DefenseSchedule findByTopic(Topic topic);
}

