package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.TopicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicUserRepo extends JpaRepository<TopicUser, Long> {}
