package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {}
