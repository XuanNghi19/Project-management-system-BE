package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassTopicRepo extends JpaRepository<ClassTopic, Long> {
    boolean existsByTopicSemester(TopicSemester topicSemester);
    boolean existsByMajor(Major major);

    @Query("""
    select c from ClassTopic c where (:name is null or lower(c.className) like lower(concat( '%', :name, '%')))
    and (:topicSemester is null or c.topicSemester = :topicSemester)
    and (:major is null or c.major = :major)
    order by c.id desc
    """)
    Page<ClassTopic> findAllClassTopic(
            @Param("name") String name,
            @Param("topicSemester") TopicSemester topicSemester,
            @Param("major") Major major,
            Pageable pageable
    );
}
