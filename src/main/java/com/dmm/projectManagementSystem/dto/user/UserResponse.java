package com.dmm.projectManagementSystem.dto.user;

import com.dmm.projectManagementSystem.model.Course;
import com.dmm.projectManagementSystem.model.Department;
import com.dmm.projectManagementSystem.model.Major;
import com.dmm.projectManagementSystem.model.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponse {
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

    Course course;
    Department department;
    Major major;

    public static UserResponse fromUser(
            User user
    ) {
        return UserResponse.builder()
                .idNum(user.getIdNum())
                .name(user.getName())
                .age(user.getAge())
                .dob(user.getDob())
                .cccd(user.getCccd())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .sex(user.getSex())
                .avatarUrl(user.getAvatarUrl())
                .address(user.getAddress())
                .course(user.getCourse())
                .department(user.getDepartment())
                .major(user.getMajor())
                .build();
    }
}
