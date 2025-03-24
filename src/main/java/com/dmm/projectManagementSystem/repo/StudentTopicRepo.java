package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.ClassTopic;
import com.dmm.projectManagementSystem.model.StudentTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentTopicRepo extends JpaRepository<StudentTopic, Long> {
    Optional<StudentTopic> findByStudentId(Long id);

    Optional<ClassTopic> findByClassTopicId(Long studentTopicId);
}
