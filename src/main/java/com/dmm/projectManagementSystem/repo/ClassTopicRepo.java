package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.ClassTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassTopicRepo extends JpaRepository<ClassTopic,Long> {

    List<ClassTopic> findByTeacherId(Long id);
}
