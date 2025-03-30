package com.dmm.projectManagementSystem.service.admin.userManagement;

import com.dmm.projectManagementSystem.dto.user.*;
import com.dmm.projectManagementSystem.enums.Role;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserManagementService {
    boolean addTeachers(List<CreateUserRequest> createUserRequests) throws Exception;
    boolean addStudent(List<CreateUserRequest> createUserRequests) throws Exception;
    boolean addAdmin(CreateUserRequest createUserRequest) throws Exception;
    boolean updateUser(UpdateUserRequest updateUserRequest) throws Exception;
    boolean deleteUser(String idNum) throws Exception;
    boolean changePassword(String newPassword, String idNum) throws Exception;
    boolean uploadAvatar(String idNum, MultipartFile[] avatar) throws Exception;

    UserListByPageResponse getAllUser(Role role, Long departmentId, Long majorId, Long courseId, String name, int page, int limit);
}
