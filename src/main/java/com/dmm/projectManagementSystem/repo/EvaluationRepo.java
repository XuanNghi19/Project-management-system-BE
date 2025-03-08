package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepo extends JpaRepository<Evaluation, Long> {}
