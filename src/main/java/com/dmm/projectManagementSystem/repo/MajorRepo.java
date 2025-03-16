package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.model.Department;
import com.dmm.projectManagementSystem.model.Major;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepo extends JpaRepository<Major, Long> {
    @Query("""
        select m from Major m where 
            (:name is null or lower(m.name) like lower(concat( '%', :name, '%')))
            and (:department is null or m.department = :department)
        order by m.id desc
    """)
    Page<Major> findAllMajor(@Param("name") String name,@Param("department") Department department, Pageable pageable);
}
