package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Course;
import com.dmm.projectManagementSystem.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
    @Query("""
        select c from Course c where (:name is null or lower(c.name) like lower(concat( '%', :name, '%')))
        order by c.id desc
    """)
    Page<Course> findAllCourse(@Param("name") String name, Pageable pageable);
}
