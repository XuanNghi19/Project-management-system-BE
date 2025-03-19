package com.dmm.projectManagementSystem.service.admin.userManagement;

import com.dmm.projectManagementSystem.dto.user.*;
import com.dmm.projectManagementSystem.enums.Role;
import com.dmm.projectManagementSystem.model.Department;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserManagementService {
    boolean addTeachers(List<CreateUserRequest> createUserRequests);
    boolean addStudent(List<CreateUserRequest> createUserRequests);
    boolean addAdmin(CreateUserRequest createUserRequest);
    boolean updateUser(UpdateUserRequest updateUserRequest, MultipartFile[] avatarImg);
    boolean deleteUser(String idNum);

    UserListByPageResponse getAllUser(Role role, Long departmentId, Long majorId, Long courseId, String name, int page, int limit);
    boolean changePassword(String newPassword, String idNum);
}
