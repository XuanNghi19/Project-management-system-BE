package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.ClassTopic;
import com.dmm.projectManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassTopicRepo extends JpaRepository<ClassTopic, Long> {
    Optional<User> findByTeacherId(Long classTopicId);
}
