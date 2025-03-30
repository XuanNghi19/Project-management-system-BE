package com.dmm.projectManagementSystem.config;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.user.CreateUserRequest;
import com.dmm.projectManagementSystem.enums.Role;
import com.dmm.projectManagementSystem.model.Department;
import com.dmm.projectManagementSystem.model.User;
import com.dmm.projectManagementSystem.repo.DepartmentRepo;
import com.dmm.projectManagementSystem.repo.UserRepo;
import com.dmm.projectManagementSystem.service.admin.userManagement.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {

    final private UserRepo userRepo;
    final private UserManagementService userManagementService;
    final private DepartmentRepo departmentRepo;
    final private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(departmentRepo.count() == 0) {
            departmentRepo.save(
                    Department.fromCRUDDepartment(
                            CRUDDepartment.builder()
                                    .name("Template")
                                    .description("")
                                    .build()
                    )
            );
        }

        if (userRepo.existsByRole(Role.ADMIN)) {
            System.out.println("Admin đã tồn tại, không cần tạo mới.");
            return;
        }

        System.out.println("Không tìm thấy admin, đang tạo admin mặc định...");

        CreateUserRequest request = CreateUserRequest.builder()
                .name("Admin")
                .departmentId(1L)
                .password("123")
                .build();

        userManagementService.addAdmin(request);

        System.out.println("Admin mặc định đã được tạo thành công.");
    }
}

