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

    @Transactional
    @Override
    public boolean addTeachers(List<CreateUserRequest> createUserRequests) {
        try {
            for (var x : createUserRequests) {
                Department department = departmentRepo.findById(x.getDepartmentId())
                        .orElseThrow(() -> new RuntimeException("Khong tim thay departmentId: " + x.getDepartmentId()));

                String encodePassword = passwordEncoder.encode(x.getPassword());

                User newTeacher = userRepo.save(User.fromCreateUserRequest(x, encodePassword, Role.INSTRUCTORS));
                newTeacher.setDepartment(department);
                newTeacher.setIdNum("GV" + StringUtils.getInitials(department.getName()) + newTeacher.getId());

                userRepo.save(newTeacher);
            }

            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            return false;
        }
    }

    @Transactional
    @Override
    public boolean addStudent(List<CreateUserRequest> createUserRequests) {
        try {
            for (var x : createUserRequests) {
                Major major = majorRepo.findById(x.getMajorId())
                        .orElseThrow(() -> new RuntimeException("Khong ton tai major voi idNum: " + x.getMajorId()));
                Course course = courseRepo.findById(x.getCourseId())
                        .orElseThrow(() -> new RuntimeException("Khong ton tai course voi idNum: " + x.getCourseId()));

                String encodePassword = passwordEncoder.encode(x.getPassword());

                User newStudent = userRepo.save(User.fromCreateUserRequest(x, encodePassword, Role.STUDENT));
                newStudent.setMajor(major);
                newStudent.setCourse(course);
                newStudent.setIdNum("SV" + StringUtils.getInitials(major.getName()) + newStudent.getId());

                userRepo.save(newStudent);
            }
            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            return false;
        }
    }

    @Transactional
    @Override
    public boolean addAdmin(CreateUserRequest createUserRequest) {
        try {
            Department department = departmentRepo.findById(createUserRequest.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Khong tim thay departmentId: " + createUserRequest.getDepartmentId()));

            String encodePassword = passwordEncoder.encode(createUserRequest.getPassword());

            User newAdmin = userRepo.save(User.fromCreateUserRequest(createUserRequest, encodePassword, Role.ADMIN));
            newAdmin.setDepartment(department);
            newAdmin.setIdNum("AM" + StringUtils.getInitials(department.getName()) + newAdmin.getId());

            userRepo.save(newAdmin);

            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            return false;
        }
    }

    @Transactional
    @Override
    public boolean updateUser(UpdateUserRequest updateUserRequest, MultipartFile[] avatarImg) {
        try {
            User exUser = userRepo.findByIdNum(updateUserRequest.getIdNum())
                    .orElseThrow(() -> new RuntimeException("Khong ton tai user voi idNum: " + updateUserRequest.getIdNum()));

            List<String> avatarUrl = firebaseService.uploadFiles(avatarImg);

            User updateUser;
            String encodePassword = passwordEncoder.encode(updateUserRequest.getPassword());

            if (exUser.getRole() == Role.ADMIN || exUser.getRole() == Role.INSTRUCTORS) {
                Department department = departmentRepo.findById(updateUserRequest.getDepartmentId())
                        .orElseThrow(() -> new RuntimeException("Khong tim thay departmentId: " + updateUserRequest.getDepartmentId()));

                updateUser = User.fromUpdateUserRequest(updateUserRequest, encodePassword, avatarUrl.get(0));
                updateUser.setId(exUser.getId());
                updateUser.setDepartment(department);
            } else {
                Major major = majorRepo.findById(updateUserRequest.getMajorId())
                        .orElseThrow(() -> new RuntimeException("Khong ton tai major voi idNum: " + updateUserRequest.getMajorId()));
                Course course = courseRepo.findById(updateUserRequest.getCourseId())
                        .orElseThrow(() -> new RuntimeException("Khong ton tai course voi idNum: " + updateUserRequest.getCourseId()));

                updateUser = User.fromUpdateUserRequest(updateUserRequest, encodePassword, avatarUrl.get(0));
                updateUser.setId(exUser.getId());
                updateUser.setMajor(major);
                updateUser.setCourse(course);
            }

            userRepo.save(updateUser);

            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            return false;
        }
    }

    @Transactional
    @Override
    public boolean changePassword(String newPassword, String idNum) {
        try {
            User exUser = userRepo.findByIdNum(idNum)
                    .orElseThrow(() -> new RuntimeException("khong tim thay user voi idNum: " + idNum));

            String encodePassword = passwordEncoder.encode(newPassword);
            exUser.setPassword(encodePassword);
            userRepo.save(exUser);

            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            return false;
        }
    }

    @Transactional
    @Override
    public boolean deleteUser(String idNum) {
        Optional<User> exUser = userRepo.findByIdNum(idNum);
        try {
            if (exUser.isPresent()) {
                exUser.get().setActive(false);
                userRepo.save(exUser.get());

                return true;
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            return false;
        }
        return false;
    }

    @Override
    public UserListByPageResponse getAllUser(Role role, Long departmentId, Long majorId, Long courseId, String name, int page, int limit) {
        Department department = null;
        Major major = null;
        Course course = null;

        if (departmentId != null) {
            department = departmentRepo.findById(departmentId)
                    .orElseThrow(() -> new RuntimeException("Khong tim thay departmentId: " + departmentId));
        }
        if (majorId != null) {
            major = majorRepo.findById(majorId)
                    .orElseThrow(() -> new RuntimeException("Khong tim thay majorId: " + majorId));
        }
        if (courseId != null) {
            course = courseRepo.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Khong tim thay courseId: " + courseId));
        }

        Page<UserResponse> userResponsePage = userRepo.findAllUser(
                        role,
                        department,
                        major,
                        course,
                        name,
                        PageRequest.of(page, limit)
                )
                .map(UserResponse::fromUser);

        return UserListByPageResponse.fromSplitPage(userResponsePage.getContent(), userResponsePage.getTotalPages(), page, limit);
    }

}