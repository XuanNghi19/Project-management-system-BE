package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Task;
import com.dmm.projectManagementSystem.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    boolean existsByTopic(Topic topic);
    Long deleteAllByTopic(Topic topic);

    List<Task> findAllByTopic(Topic topic);
    Page<Task> findByTopicId(Long id, Pageable pageable);
}