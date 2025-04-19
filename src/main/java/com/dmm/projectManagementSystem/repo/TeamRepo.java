package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.enums.TeamStatus;
import com.dmm.projectManagementSystem.model.Team;
import com.dmm.projectManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepo extends JpaRepository<Team, Long> {

    Optional<Team> findByTopicId(Long topicId);
}
