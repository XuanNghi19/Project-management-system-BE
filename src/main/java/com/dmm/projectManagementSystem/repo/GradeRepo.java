package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Grade;
import com.dmm.projectManagementSystem.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepo extends JpaRepository<Grade, Long> {
}
