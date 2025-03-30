package com.dmm.projectManagementSystem.dto.user;

import com.dmm.projectManagementSystem.dto.course.CRUDCourse;
import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;
import com.dmm.projectManagementSystem.model.TopicSemester;
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

    CRUDCourse course;
    CRUDDepartment department;
    CRUDMajor major;

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
                .course(user.getCourse() != null ? CRUDCourse.fromCourse(user.getCourse()) : null)
                .department(user.getDepartment() != null ? CRUDDepartment.fromDepartment(user.getDepartment()) : null)
                .major(user.getMajor() != null ? CRUDMajor.fromMajor(user.getMajor()) : null)
                .build();
    }
}
