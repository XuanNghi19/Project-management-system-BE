package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.ClassTopic;
import com.dmm.projectManagementSystem.model.StudentTopic;
import com.dmm.projectManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentTopicRepo extends JpaRepository<StudentTopic, Long> {
    List<StudentTopic> findAllByStudent(User student);
    boolean existsByClassTopic(ClassTopic classTopic);

    void deleteAllByClassTopic(ClassTopic classTopic);
    List<StudentTopic> findAllByClassTopic(ClassTopic classTopic);
}
