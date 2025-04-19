package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    Page<Task> findByTopicId(Long id, Pageable pageable);
}