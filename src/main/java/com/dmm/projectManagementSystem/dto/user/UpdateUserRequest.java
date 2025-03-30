package com.dmm.projectManagementSystem.dto.user;

import com.dmm.projectManagementSystem.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    String idNum;

    String name;
    int age;
    String dob;
    String cccd;
    String email;
    String phoneNumber;
    String sex;
    String address;

    Long courseId;
    Long departmentId;
    Long majorId;

}
