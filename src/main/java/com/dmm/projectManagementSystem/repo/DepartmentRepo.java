package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {

    @Query("""
        select d from Department d where (:name is null or lower(d.name) like lower(concat( '%', :name, '%')))
        order by d.id desc
    """)
    Page<Department> findAllDepartment(@Param("name") String name, Pageable pageable);

    @Query("""
        select d from Department d where (:name is null or lower(d.name) like lower(concat( '%', :name, '%')))
        order by d.id desc
    """)
    List<Department> findAllDepartmentByName(@Param("name") String name);
}
