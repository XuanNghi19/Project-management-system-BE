package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Council;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouncilRepo extends JpaRepository<Council, Long> {}