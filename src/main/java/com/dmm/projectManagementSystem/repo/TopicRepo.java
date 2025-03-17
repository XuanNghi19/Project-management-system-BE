package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {
    Topic findByIdNum(String idNum);
}
