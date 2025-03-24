package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.enums.TeamStatus;
import com.dmm.projectManagementSystem.model.Team;
import com.dmm.projectManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepo extends JpaRepository<Team, Long> {
//    boolean existsByUser (User user);
//    List<Team> findBySubmitted (boolean status);
//    List<Team> findByStatus(TeamStatus teamStatus);


}
