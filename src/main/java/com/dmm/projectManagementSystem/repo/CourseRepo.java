package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {}
