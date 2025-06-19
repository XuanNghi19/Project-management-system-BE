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

    @Query(value = """
            select * from council where (:name is null or lower(name) like lower(concat( '%', :name, '%')))
            and (:topic_semester_id is null or topic_semester_id = :topic_semester_id)
            and (:department_id is null or department_id = :department_id)
            and (
                (CAST(:start AS TIMESTAMP) is null and CAST(:end AS TIMESTAMP) is null)
                or (CAST(:start AS TIMESTAMP) is not null and CAST(:end AS TIMESTAMP) is not null and start_time >= CAST(:start AS TIMESTAMP) and end_time <= CAST(:end AS TIMESTAMP))
                or (CAST(:start AS TIMESTAMP) is not null and CAST(:end AS TIMESTAMP) is null and start_time >= CAST(:start AS TIMESTAMP))
                or (CAST(:start AS TIMESTAMP) is null and CAST(:end AS TIMESTAMP) is not null and end_time <= CAST(:end AS TIMESTAMP))
            )
            order by id desc
            """, nativeQuery = true)
    Page<Council> findAllCouncil(
            @Param("name") String name,
            @Param("topic_semester_id") Long topicSemesterID,
            @Param("department_id") Long departmentID,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );
}