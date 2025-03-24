package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.StudentTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentTopicRepo extends JpaRepository<StudentTopic,Long> {
    List<StudentTopic> findByClassTopicId(Long id);
}
