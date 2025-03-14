package com.dmm.projectManagementSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DutyRepo extends JpaRepository<Duty, Long> {}
