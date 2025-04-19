package com.dmm.projectManagementSystem.dto.group.res;


import com.dmm.projectManagementSystem.dto.user.UserResponse;
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
public class TeacherTeamResDTO {
    String idNum;
    String name;
    Course course;
    Department department;

    public static TeacherTeamResDTO loadFromTeacherRes(
            User user
    ) {
        return TeacherTeamResDTO.builder()
                .idNum(user.getIdNum())
                .name(user.getName())
                .course(user.getCourse())
                .department(user.getDepartment())
                .build();
    }

}
