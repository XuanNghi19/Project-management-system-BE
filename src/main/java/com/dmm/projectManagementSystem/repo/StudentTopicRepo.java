package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.StudentTopic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentTopicRepo extends JpaRepository<StudentTopic, Long> {
}
