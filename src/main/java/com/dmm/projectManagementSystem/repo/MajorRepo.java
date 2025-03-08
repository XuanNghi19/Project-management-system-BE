package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepo extends JpaRepository<Major, Long> {}
