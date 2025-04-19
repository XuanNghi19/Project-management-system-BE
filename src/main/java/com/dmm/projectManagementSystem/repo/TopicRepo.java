package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Topic;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {
    boolean existsById( Long id);
}
