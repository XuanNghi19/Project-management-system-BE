package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Evaluation;
import com.dmm.projectManagementSystem.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepo extends JpaRepository<Evaluation, Long> {
    boolean existsByTopic(Topic topic);
    boolean deleteAllByTopic(Topic topic);

    List<Evaluation> findAllByTopic(Topic topic);
}
