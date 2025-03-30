package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Council;
import com.dmm.projectManagementSystem.model.TopicSemester;
import com.dmm.projectManagementSystem.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CouncilRepo extends JpaRepository<Council, Long> {
    boolean existsByDepartment(Department department);

    boolean existsByTopicSemester(TopicSemester topicSemester);

    @Query("""
            select c from Council c where (:name is null or lower(c.name) like lower(concat( '%', :name, '%')))
            and (:topicSemester is null or c.topicSemester = :topicSemester)
            and (:department is null or c.department = :department)
            and (
                (:start is null and :end is null)
                or (:start is not null and :end is not null and c.startTime >= :start and c.endTime <= :end)
                or (:start is not null and :end is null and c.startTime >= :start)
                or (:start is null and :end is not null and c.endTime <= :end)
            )
            order by c.id desc
            """)
    Page<Council> findAllCouncil(
            @Param("name") String name,
            @Param("topicSemester") TopicSemester topicSemester,
            @Param("department") Department department,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );
}