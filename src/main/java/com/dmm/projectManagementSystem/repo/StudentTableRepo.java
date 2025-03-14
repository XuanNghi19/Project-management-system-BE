package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.StudentTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentTableRepo extends JpaRepository<StudentTable, Long> {}
