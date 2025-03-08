package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepo extends JpaRepository<Instructor, Long> {}
