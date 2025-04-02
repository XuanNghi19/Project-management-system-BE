package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.ClassTopic;
import com.dmm.projectManagementSystem.model.StudentTopic;
import com.dmm.projectManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentTopicRepo extends JpaRepository<StudentTopic, Long> {
    List<StudentTopic> findAllByStudent(User student);

    boolean existsByClassTopic(ClassTopic classTopic);

    void deleteAllByClassTopic(ClassTopic classTopic);

    @Query(value = """
            select * from student_topic where class_topic_id = :class_topic_id
            order by id desc
            """, nativeQuery = true)
    List<StudentTopic> findAllByClassTopic(@Param("class_topic_id") Long classTopicID);

    StudentTopic findByClassTopic(ClassTopic classTopic);
}
