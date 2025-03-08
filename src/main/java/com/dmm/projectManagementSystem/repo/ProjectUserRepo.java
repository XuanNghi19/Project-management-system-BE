package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectUserRepo extends JpaRepository<ProjectUser, Long> {}
