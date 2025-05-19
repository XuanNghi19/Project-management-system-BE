package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Department;
import com.dmm.projectManagementSystem.model.Major;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorRepo extends JpaRepository<Major, Long> {
    @Query(value = """
        select * from major where (:name is null or lower(name) like lower(concat( '%', :name, '%')))
            and (:department_id is null or department_id = :department_id)
        order by id desc
    """, nativeQuery = true)
    Page<Major> findAllMajor(@Param("name") String name, @Param("department_id") Long departmentID, Pageable pageable);

    boolean existsByDepartment(Department department);

    @Query(value = """
        select * from major where (:name is null or lower(name) like lower(concat( '%', :name, '%')))
        order by id desc
    """, nativeQuery = true)
    List<Major> findAllMajorByName(@Param("name") String name);
}
