package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.enums.Role;
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
    boolean existsByCourse(Course course);
    boolean existsByIdNum(String idNum);
    boolean deleteByIdNum(String idNum);

    @Query("""
            select t from Topic t
            where (:major is null or t.major = :major)
            and (:course is null or t.course = :course)
            and (:name is null or lower(t.name) like lower(concat('%', :name, '%')))
            order by t.id desc
    """)
    Page<Topic> findAllTopic(
            @Param("major") Major major,
            @Param("course") Course course,
            @Param("name") String name,
            Pageable pageable
    );
}
