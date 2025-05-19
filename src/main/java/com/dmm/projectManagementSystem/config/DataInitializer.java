package com.dmm.projectManagementSystem.config;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.enums.Role;
import com.dmm.projectManagementSystem.model.Department;
import com.dmm.projectManagementSystem.model.User;
import com.dmm.projectManagementSystem.repo.DepartmentRepo;
import com.dmm.projectManagementSystem.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepo departmentRepo;

    public DataInitializer(UserRepo userRepository, PasswordEncoder passwordEncoder,  DepartmentRepo departmentRepo) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.departmentRepo = departmentRepo;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByIdNum("SV0024").isEmpty()) {
            CRUDDepartment crudDepartment = new CRUDDepartment();
            crudDepartment.setName("Khoa CNTT");
            crudDepartment.setDescription("Chuyên đào tạo phần mềm, mạng máy tính,...");

            Department department = Department.fromCRUDDepartment(crudDepartment);
            departmentRepo.save(department);

            User user = new User();
            user.setIdNum("SV0024");
            user.setName("Nguyễn Văn A");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setActive(true);
            user.setRole(Role.STUDENT);
            user.setEmail("a1@example.com");
            user.setActive(true);
            user.setDepartment(department);

            userRepository.save(user);
            System.out.println("Đã tạo user mặc định: SV0024");
        }
    }
}
