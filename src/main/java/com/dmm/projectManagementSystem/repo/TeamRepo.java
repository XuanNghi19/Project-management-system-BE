package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepo extends JpaRepository<Team,Long> {
    List<Team> findByTeacher_Id(Long id);
}
