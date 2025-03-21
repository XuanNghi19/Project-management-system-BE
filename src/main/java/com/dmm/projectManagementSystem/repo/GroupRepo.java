package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Topic;
import com.dmm.projectManagementSystem.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends JpaRepository<Group, Long> {
    boolean existsByTopic(Topic topic);
    boolean deleteAllByTopic(Topic topic);

    Group findByTopic(Topic topic);
}
