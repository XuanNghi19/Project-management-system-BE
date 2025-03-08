package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.UserEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEvaluationRepo extends JpaRepository<UserEvaluation, Long> {}
