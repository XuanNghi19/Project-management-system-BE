package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Team;
import com.dmm.projectManagementSystem.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepo extends JpaRepository<TeamMember, Long> {
    List<TeamMember> findAllByTeam(Team team);
}
