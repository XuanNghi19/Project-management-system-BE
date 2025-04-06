package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMemberRepo extends JpaRepository<TeamMember, Long> {

    Optional<List<TeamMember>> findByStudentId(Long studentId);

    List<TeamMember> findByTeamId(Long teamId);

    int deleteByStudentId(Long studentId);

    Optional<TeamMember> findByStudentIdAndTeamId(Long studentId, Long teamId);

    int deleteByTeamId(Long teamId);

}
