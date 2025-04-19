package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Meeting;
import com.dmm.projectManagementSystem.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepo extends JpaRepository<Meeting, Long> {
    boolean existsByTopic(Topic topic);
    Long deleteAllByTopic(Topic topic);

    List<Meeting> findAllByTopic(Topic topic);
    Page<Meeting> findByTopicId(Long topicId, Pageable pageable);
}
