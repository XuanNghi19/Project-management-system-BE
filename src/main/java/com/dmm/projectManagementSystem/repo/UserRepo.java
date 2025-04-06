package com.dmm.projectManagementSystem.repo;

import com.dmm.projectManagementSystem.enums.Role;
import com.dmm.projectManagementSystem.model.Course;
import com.dmm.projectManagementSystem.model.Department;
import com.dmm.projectManagementSystem.model.Major;
import com.dmm.projectManagementSystem.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByIdNum(String idNum);

    @Query("""
            select u from User u
            where u.role = :role
            and (:department is null or u.department = :department)
            and (:major is null or u.major = :major)
            and (:course is null or u.course = :course)
            and (:name is null or lower(u.name) like lower(concat('%', :name, '%')))
            order by u.id desc
    """)
    Page<User> findAllUser(
            @Param("role") Role role,
            @Param("department")  Department department,
            @Param("major") Major major,
            @Param("course") Course course,
            @Param("name") String name,
            Pageable pageable
    );

    List<User> findByIdIn(List<Long> listStudentIds);
}















