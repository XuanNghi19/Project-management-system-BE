package com.dmm.projectManagementSystem.dto.studentTopic;

import com.dmm.projectManagementSystem.dto.user.UserResponse;
import com.dmm.projectManagementSystem.enums.ActionTypes;
import com.dmm.projectManagementSystem.model.StudentTopic;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.awt.event.ActionEvent;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StudentTopicResponse {
    Long id;
    UserResponse student;
    boolean status;

    static public StudentTopicResponse fromStudentTopic(StudentTopic studentTopic) {
        return StudentTopicResponse.builder()
                .id(studentTopic.getId())
                .student(UserResponse.fromUser(studentTopic.getStudent()))
                .status(studentTopic.isStatus())
                .build();
    }
}
