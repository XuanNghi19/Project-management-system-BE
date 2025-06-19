package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Announcement;
import com.dmm.projectManagementSystem.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement, Long> {
    boolean existsByTeam(Team team);
    Long deleteAllByTeam(Team team);

    List<Announcement> findAllByTeam(Team team);
}
