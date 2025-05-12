package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {
    Topic findByIdNum(String idNum);
    boolean existsByMajor(Major major);
    boolean existsByTopicSemester(TopicSemester topicSemester);
    boolean existsByIdNum(String idNum);
    Long deleteByIdNum(String idNum);

    @Query(value = """
            select * from topic
            where (:major_id is null or major_id = :major_id)
            and (:topic_semester_id is null or topic_semester_id = :topic_semester_id)
            and (:name is null or lower(name) like lower(concat('%', :name, '%')))
            and (:project_stage is null or project_stage = :project_stage)
            order by id desc
    """, nativeQuery = true)
    Page<Topic> findAllTopic(
            @Param("major_id") Long majorID,
            @Param("topic_semester_id") Long topicSemesterID,
            @Param("name") String name,
            @Param("project_stage") String projectStage,
            Pageable pageable
    );
}
