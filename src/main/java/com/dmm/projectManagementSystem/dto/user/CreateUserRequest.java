package com.dmm.projectManagementSystem.dto.user;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {
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
