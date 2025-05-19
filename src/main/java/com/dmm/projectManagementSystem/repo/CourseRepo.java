package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Course;
import com.dmm.projectManagementSystem.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
    @Query(value = """
                select * from course where
                (:name is null or lower(name) like lower(concat( '%', :name, '%')))
                and (
                    (CAST(:start AS TIMESTAMP) is null and CAST(:end AS TIMESTAMP) is null)
                    or (CAST(:start AS TIMESTAMP) is not null and CAST(:end AS TIMESTAMP) is not null and start_time >= CAST(:start AS TIMESTAMP) and end_time <= CAST(:end AS TIMESTAMP))
                    or (CAST(:start AS TIMESTAMP) is not null and CAST(:end AS TIMESTAMP) is null and start_time >= CAST(:start AS TIMESTAMP))
                    or (CAST(:start AS TIMESTAMP) is null and CAST(:end AS TIMESTAMP) is not null and end_time <= CAST(:end AS TIMESTAMP))
                )
                order by id desc
            """, nativeQuery = true)
    Page<Course> findAllCourse(
            @Param("name") String name,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    @Query(value = """
                select * from course where (:name is null or lower(name) like lower(concat( '%', :name, '%')))
                order by id desc
            """, nativeQuery = true)
    List<Course> findAllCourseByName(@Param("name") String name);
}
