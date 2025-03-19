package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Topic;
import com.dmm.projectManagementSystem.model.TopicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicUserRepo extends JpaRepository<TopicUser, Long> {
    boolean existsByTopic(Topic topic);
    boolean deleteAllByTopic(Topic topic);
}
