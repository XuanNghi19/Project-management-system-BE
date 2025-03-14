package com.dmm.projectManagementSystem.service.admin.userManagement;

import com.dmm.projectManagementSystem.dto.user.*;
import com.dmm.projectManagementSystem.model.Department;

import java.util.List;

public interface UserManagementService {
    boolean addTeachers(List<CreateUserRequest> createUserRequests);
    boolean addStudent(List<CreateUserRequest> createUserRequests);
    boolean addAdmin(CreateUserRequest createUserRequest);
    boolean updateUser(UpdateUserRequest updateUserRequest);
    boolean deleteUser(String idNum);

    UserListByPageResponse getAllTeacher(Long departmentId, String name, int page, int limit);
    UserListByPageResponse getAllStudent(Long majorId, Long courseId, String name, int page, int limit);
    UserListByPageResponse getAllAdmin(Long departmentId, String name, int page, int limit);
}
