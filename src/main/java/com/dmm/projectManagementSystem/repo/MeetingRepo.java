package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Meeting;
import com.dmm.projectManagementSystem.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepo extends JpaRepository<Meeting, Long> {
    boolean existsByTopic(Topic topic);
    boolean deleteAllByTopic(Topic topic);
}
