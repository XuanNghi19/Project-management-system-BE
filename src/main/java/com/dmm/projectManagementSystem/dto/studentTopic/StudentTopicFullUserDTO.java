package com.dmm.projectManagementSystem.dto.studentTopic;

import com.dmm.projectManagementSystem.model.Course;
import com.dmm.projectManagementSystem.model.Department;
import com.dmm.projectManagementSystem.model.Major;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentTopicFullUserDTO {
    private Long id;
    private String idNum;
    private String name;
    private int age;
    private String dob;
    private String cccd;
    private String email;
    private String phoneNumber;
    private String sex;
    private String avatarUrl;
    private String address;
    private boolean active;
    private String role;
    private Course course;
    private Department department;
    private Major major;
    private boolean status;
}