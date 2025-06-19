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

    @Query(value = """
            select * from app_user
            where role = :role
            and active = true
            and (:department_id is null or department_id = :department_id)
            and (:major_id is null or major_id = :major_id)
            and (:course_id is null or course_id = :course_id)
            and (:name is null or lower(name) like lower(concat('%', :name, '%')))
            order by id desc
    """, nativeQuery = true)
    Page<User> findAllUser(
            @Param("role") String role,
            @Param("department_id") Long departmentID,
            @Param("major_id") Long majorID,
            @Param("course_id") Long courseID,
            @Param("name") String name,
            Pageable pageable
    );

    boolean existsByMajor(Major major);
    boolean existsByDepartment(Department department);
    boolean existsByCourse(Course course);

    boolean existsByRole(Role role);


    @Query("""
            select u from User u
            where u.role = :role
            and (:department is null or u.department = :department)
            and (:major is null or u.major = :major)
            and (:course is null or u.course = :course)
            order by u.id desc
    """)
    Page<User> findAllStudent(
            @Param("role") Role role,
            @Param("department")  Department department,
            @Param("major") Major major,
            @Param("course") Course course,
            Pageable pageable
    );
    List<User> findByIdIn(List<Long> listStudentIds);
}















