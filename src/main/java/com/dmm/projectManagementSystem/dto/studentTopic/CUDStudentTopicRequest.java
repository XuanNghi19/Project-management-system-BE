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
public class CUDStudentTopicRequest {
    Long id;
    String studentIdNum;
    boolean status;

    ActionTypes action;
}
