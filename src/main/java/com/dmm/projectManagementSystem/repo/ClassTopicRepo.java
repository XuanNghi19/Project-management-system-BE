package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassTopicRepo extends JpaRepository<ClassTopic, Long> {
    Optional<User> findByTeacherId(Long classTopicId);
    boolean existsByTopicSemester(TopicSemester topicSemester);

    boolean existsByMajor(Major major);

    @Query(value = """
            select * from class_topic where (:name is null or lower(class_name) like lower(concat( '%', :name, '%')))
            and (:topic_semester_id is null or topic_semester_id = :topic_semester_id)
            and (:major_id is null or major_id = :major_id)
            order by id desc
            """, nativeQuery = true)
    Page<ClassTopic> findAllClassTopic(
            @Param("name") String name,
            @Param("topic_semester_id") Long topicSemesterID,
            @Param("major_id") Long majorID,
            Pageable pageable
    );

    List<ClassTopic> findByTeacher_Id(Long id);
}
