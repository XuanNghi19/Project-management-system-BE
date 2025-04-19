package com.dmm.projectManagementSystem.dto.classTopic;

import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;
import com.dmm.projectManagementSystem.dto.user.UserResponse;
import com.dmm.projectManagementSystem.model.ClassTopic;
import com.dmm.projectManagementSystem.model.Major;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClassTopicResponse {
    Long id;
    UserResponse teacher;

    String className;
    LocalDateTime startRegistrationTime;
    LocalDateTime endRegistrationTime;

    CRUDTopicSemester topicSemester;
    CRUDMajor major;

    static public ClassTopicResponse fromClassTopic(ClassTopic classTopic){
        return ClassTopicResponse.builder()
                .id(classTopic.getId())
                .teacher(UserResponse.fromUser(classTopic.getTeacher()))
                .className(classTopic.getClassName())
                .startRegistrationTime(classTopic.getStartRegistrationTime())
                .endRegistrationTime(classTopic.getEndRegistrationTime())
                .topicSemester(CRUDTopicSemester.fromTopicSemester(classTopic.getTopicSemester()))
                .major(CRUDMajor.fromMajor(classTopic.getMajor()))
                .build();
    }
}
