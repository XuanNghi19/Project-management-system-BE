package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Task;
import com.dmm.projectManagementSystem.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    boolean existsByTopic(Topic topic);
    boolean deleteAllByTopic(Topic topic);
}