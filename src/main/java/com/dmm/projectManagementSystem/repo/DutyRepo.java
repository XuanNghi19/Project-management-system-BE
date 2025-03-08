package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Duty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DutyRepo extends JpaRepository<Duty, Long> {}
