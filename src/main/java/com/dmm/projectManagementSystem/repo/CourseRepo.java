package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Course;
import com.dmm.projectManagementSystem.model.TopicSemester;
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
    @Query("""
                select c from Course c where
                (:name is null or lower(c.name) like lower(concat( '%', :name, '%')))
                and (
                    (:start is null and :end is null)
                    or (:start is not null and :end is not null and c.startTime >= :start and c.endTime <= :end)
                    or (:start is not null and :end is null and c.startTime >= :start)
                    or (:start is null and :end is not null and c.endTime <= :end)
                )
            
                order by c.id desc
            """)
    Page<Course> findAllCourse(
            @Param("name") String name,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    @Query("""
                select c from Course c where (:name is null or lower(c.name) like lower(concat( '%', :name, '%')))
                order by c.id desc
            """)
    List<Course> findAllCourseByName(@Param("name") String name);
}
