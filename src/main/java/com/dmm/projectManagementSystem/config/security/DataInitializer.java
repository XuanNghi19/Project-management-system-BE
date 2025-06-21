package com.dmm.projectManagementSystem.config.security;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.enums.Role;
import com.dmm.projectManagementSystem.model.Department;
import com.dmm.projectManagementSystem.model.User;
import com.dmm.projectManagementSystem.repo.DepartmentRepo;
import com.dmm.projectManagementSystem.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepo departmentRepo;

    public DataInitializer(UserRepo userRepository, PasswordEncoder passwordEncoder, DepartmentRepo departmentRepo) {
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
            user.setName("Nguyễn Văn Anh");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setActive(true);
            user.setRole(Role.STUDENT);
            user.setEmail("a1@example.com");
            user.setActive(true);
            user.setDepartment(department);
            user.setAddress("Hà Nội");
            user.setAge(22);
            user.setDob("04-08-2003");
            user.setCccd("019203008626");
            user.setPhoneNumber("0367082538");

            userRepository.save(user);
            System.out.println("Đã tạo user mặc định: SV0024");
        }

        // Tạo sinh viên thứ 2
        if (userRepository.findByIdNum("SV0025").isEmpty()) {
            List<Department> departments = departmentRepo.findAllDepartmentByName("Khoa CNTT");
            Department department = departments.isEmpty() ? null : departments.get(0);
            if (department != null) {
                User user2 = new User();
                user2.setIdNum("SV0025");
                user2.setName("Trần Thị Bình");
                user2.setPassword(passwordEncoder.encode("123456"));
                user2.setActive(true);
                user2.setRole(Role.STUDENT);
                user2.setEmail("b2@example.com");
                user2.setDepartment(department);
                user2.setAddress("Hồ Chí Minh");
                user2.setAge(21);
                user2.setDob("15-03-2004");
                user2.setCccd("019203008627");
                user2.setPhoneNumber("0367082539");

                userRepository.save(user2);
                System.out.println("Đã tạo user mặc định: SV0025");
            }
        }

        // Tạo sinh viên thứ 3
        if (userRepository.findByIdNum("SV0026").isEmpty()) {
            List<Department> departments = departmentRepo.findAllDepartmentByName("Khoa CNTT");
            Department department = departments.isEmpty() ? null : departments.get(0);
            if (department != null) {
                User user3 = new User();
                user3.setIdNum("SV0026");
                user3.setName("Lê Văn Cường");
                user3.setPassword(passwordEncoder.encode("123456"));
                user3.setActive(true);
                user3.setRole(Role.STUDENT);
                user3.setEmail("c3@example.com");
                user3.setDepartment(department);
                user3.setAddress("Đà Nẵng");
                user3.setAge(23);
                user3.setDob("22-11-2002");
                user3.setCccd("019203008628");
                user3.setPhoneNumber("0367082540");

                userRepository.save(user3);
                System.out.println("Đã tạo user mặc định: SV0026");
            }
        }
    }
}
