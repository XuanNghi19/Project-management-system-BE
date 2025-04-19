package com.dmm.projectManagementSystem.service.admin.userManagement;

import com.dmm.projectManagementSystem.service.serviceUtils.FirebaseService;
import com.dmm.projectManagementSystem.utils.StringUtils;
import com.dmm.projectManagementSystem.dto.user.CreateUserRequest;
import com.dmm.projectManagementSystem.dto.user.UpdateUserRequest;
import com.dmm.projectManagementSystem.dto.user.UserListByPageResponse;
import com.dmm.projectManagementSystem.dto.user.UserResponse;
import com.dmm.projectManagementSystem.enums.Role;
import com.dmm.projectManagementSystem.model.*;
import com.dmm.projectManagementSystem.repo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    final private UserRepo userRepo;
    final private PasswordEncoder passwordEncoder;
    final private DepartmentRepo departmentRepo;
    final private MajorRepo majorRepo;
    final private CourseRepo courseRepo;
    final private FirebaseService firebaseService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean addTeachers(List<CreateUserRequest> createUserRequests) throws Exception {
        try {
            for (var x : createUserRequests) {
                Department department = departmentRepo.findById(x.getDepartmentId())
                        .orElseThrow(() -> new RuntimeException("Khong tim thay departmentId: " + x.getDepartmentId()));

                User newTeacher = userRepo.save(User.fromCreateUserRequest(x, department, Role.INSTRUCTORS));
                newTeacher.setIdNum("GV" + StringUtils.getInitials(department.getName()) + newTeacher.getId());
                String encodePassword = passwordEncoder.encode(newTeacher.getIdNum());
                newTeacher.setPassword(encodePassword);

                userRepo.save(newTeacher);
            }

            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            throw new Exception(ex);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean addStudent(List<CreateUserRequest> createUserRequests) throws Exception {
        try {
            for (var x : createUserRequests) {
                Major major = majorRepo.findById(x.getMajorId())
                        .orElseThrow(() -> new RuntimeException("Khong ton tai major voi idNum: " + x.getMajorId()));
                Course course = courseRepo.findById(x.getCourseId())
                        .orElseThrow(() -> new RuntimeException("Khong ton tai course voi idNum: " + x.getCourseId()));



                User newStudent = userRepo.save(User.fromCreateUserRequest(x, major.getDepartment(), Role.STUDENT));
                newStudent.setMajor(major);
                newStudent.setCourse(course);
                newStudent.setIdNum("SV" + StringUtils.getInitials(major.getName()) + newStudent.getId());
                String encodePassword = passwordEncoder.encode(newStudent.getIdNum());
                newStudent.setPassword(encodePassword);

                userRepo.save(newStudent);
            }
            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            throw new Exception(ex);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean addAdmin(CreateUserRequest createUserRequest) throws Exception {
        try {
            Department department = departmentRepo.findById(createUserRequest.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Khong tim thay departmentId: " + createUserRequest.getDepartmentId()));

            User newAdmin = userRepo.save(User.fromCreateUserRequest(createUserRequest, department, Role.ADMIN));

            newAdmin.setIdNum("AM" + StringUtils.getInitials(department.getName()) + newAdmin.getId());

            String encodePassword = passwordEncoder.encode(newAdmin.getIdNum());
            newAdmin.setPassword(encodePassword);

            userRepo.save(newAdmin);

            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            throw new Exception(ex);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean updateUser(UpdateUserRequest updateUserRequest) throws Exception {
        try {
            User exUser = userRepo.findByIdNum(updateUserRequest.getIdNum())
                    .orElseThrow(() -> new RuntimeException("Khong ton tai user voi idNum: " + updateUserRequest.getIdNum()));

            User updateUser;

            if (exUser.getRole() == Role.ADMIN || exUser.getRole() == Role.INSTRUCTORS) {
                Department department = departmentRepo.findById(updateUserRequest.getDepartmentId())
                        .orElseThrow(() -> new RuntimeException("Khong tim thay departmentId: " + updateUserRequest.getDepartmentId()));

                updateUser = User.fromUpdateUserRequest(updateUserRequest);
                updateUser.setId(exUser.getId());
                updateUser.setIdNum(exUser.getIdNum());
                updateUser.setPassword(exUser.getPassword());
                updateUser.setRole(exUser.getRole());
                updateUser.setDepartment(department);
            } else {
                Major major = majorRepo.findById(updateUserRequest.getMajorId())
                        .orElseThrow(() -> new RuntimeException("Khong ton tai major voi idNum: " + updateUserRequest.getMajorId()));
                Course course = courseRepo.findById(updateUserRequest.getCourseId())
                        .orElseThrow(() -> new RuntimeException("Khong ton tai course voi idNum: " + updateUserRequest.getCourseId()));

                updateUser = User.fromUpdateUserRequest(updateUserRequest);
                updateUser.setId(exUser.getId());
                updateUser.setIdNum(exUser.getIdNum());
                updateUser.setPassword(exUser.getPassword());
                updateUser.setMajor(major);
                updateUser.setRole(exUser.getRole());
                updateUser.setCourse(course);
            }

            userRepo.save(updateUser);

            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            throw new Exception(ex);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean changePassword(String newPassword, String idNum) throws Exception {
        try {
            User exUser = userRepo.findByIdNum(idNum)
                    .orElseThrow(() -> new RuntimeException("khong tim thay user voi idNum: " + idNum));

            String encodePassword = passwordEncoder.encode(newPassword);
            exUser.setPassword(encodePassword);
            userRepo.save(exUser);

            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            throw new Exception(ex);
        }

    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean uploadAvatar(String idNum, MultipartFile[] avatar) throws Exception {
        if(avatar.length == 0 || avatar[0].isEmpty() || avatar[0].getSize() == 0) {
            return false;
        }
        try {
            User exUser = userRepo.findByIdNum(idNum)
                    .orElseThrow(() -> new RuntimeException("khong tim thay user voi idNum: " + idNum));

            if(exUser.getAvatarUrl() != null) {
                firebaseService.deleteFile(exUser.getAvatarUrl());
            }

            exUser.setAvatarUrl(firebaseService.uploadFiles(avatar).get(0));
            userRepo.save(exUser);

            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            throw new Exception(ex);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean deleteUser(String idNum) throws Exception {
        Optional<User> exUser = userRepo.findByIdNum(idNum);
        try {
            if (exUser.isPresent()) {
                exUser.get().setActive(false);
                userRepo.save(exUser.get());

                return true;
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            throw new Exception(ex);
        }
        return false;
    }

    @Override
    public UserListByPageResponse getAllUser(Role role, Long departmentId, Long majorId, Long courseId, String name, int page, int limit) {
        Page<UserResponse> userResponsePage = userRepo.findAllUser(
                        role.toString(),
                        departmentId,
                        majorId,
                        courseId,
                        name,
                        PageRequest.of(page, limit)
                )
                .map(UserResponse::fromUser);

        return UserListByPageResponse.fromSplitPage(userResponsePage.getContent(), userResponsePage.getTotalPages(), page, limit);
    }

}