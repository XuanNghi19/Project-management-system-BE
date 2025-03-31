package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.DefenseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DefenseScheduleRepo extends JpaRepository<DefenseSchedule, Long> {
    Optional<DefenseSchedule> findByTopicId(Long topicId);
}

