package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {}