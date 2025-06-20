package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Team;
import com.dmm.projectManagementSystem.model.TeamMember;
import com.dmm.projectManagementSystem.enums.TeamStatus;
import com.dmm.projectManagementSystem.enums.MembershipPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

@Repository
public interface TeamMemberRepo extends JpaRepository<TeamMember, Long> {

    Optional<List<TeamMember>> findByStudentId(Long studentId);

    Optional<TeamMember> findFirstByStudentId(Long studentId);

    List<TeamMember> findByTeamId(Long teamId);

    int deleteByStudentId(Long studentId);

    Optional<TeamMember> findByStudentIdAndTeamId(Long studentId, Long teamId);

    int deleteByTeamId(Long teamId);

    List<TeamMember> findAllByTeam(Team team);

    List<TeamMember> findByTeamIdAndStatus(Long teamId, TeamStatus status);

    List<TeamMember> findByStudentIdAndStatus(Long studentId, TeamStatus status);

    TeamMember findByTeamIdAndPosition(Long teamId, MembershipPosition position);

    @Query("SELECT tm FROM TeamMember tm WHERE tm.student.id = :studentId AND tm.team.id = :teamId ORDER BY tm.id DESC LIMIT 1")
    Optional<TeamMember> findFirstByStudentIdAndTeamId(@Param("studentId") Long studentId,
            @Param("teamId") Long teamId);
}
