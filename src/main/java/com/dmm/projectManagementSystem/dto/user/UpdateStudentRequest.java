package com.dmm.projectManagementSystem.dto.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateStudentRequest {
    String idNum;

    String name;
    int age;
    String dob;
    String cccd;
    String email;
    String phoneNumber;
    String sex;
    String avatarUrl;
    String address;
}
