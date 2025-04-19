package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.ClassTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassTopicRepo extends JpaRepository<ClassTopic,Long> {

    List<ClassTopic> findByTeacher_Id( Long id);
}
