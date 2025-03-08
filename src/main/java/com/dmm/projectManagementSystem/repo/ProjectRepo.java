package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {}
