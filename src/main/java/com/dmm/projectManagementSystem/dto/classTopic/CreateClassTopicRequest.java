package com.dmm.projectManagementSystem.dto.classTopic;

import com.dmm.projectManagementSystem.dto.studentTopic.CUDStudentTopicRequest;
import com.dmm.projectManagementSystem.dto.studentTopic.StudentTopicResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateClassTopicRequest {
    String teacherIdNum;

    String className;
    LocalDateTime startRegistrationTime;
    LocalDateTime endRegistrationTime;

    Long topicSemesterID;
    Long majorID;

    List<CUDStudentTopicRequest> studentTopicList;
}
