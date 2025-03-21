package com.dmm.projectManagementSystem.dto.group;

import com.dmm.projectManagementSystem.enums.MembershipPosition;
import com.dmm.projectManagementSystem.model.Group;
import com.dmm.projectManagementSystem.model.GroupStudent;
import com.dmm.projectManagementSystem.model.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class GroupStudentResponse {
    Long id;
    User student;
    MembershipPosition position;

    static public GroupStudentResponse fromGroupStudent(GroupStudent groupStudent) {
        return GroupStudentResponse.builder()
                .id(groupStudent.getId())
                .student(groupStudent.getStudent())
                .position(groupStudent.getPosition())
                .build();
    }
}
