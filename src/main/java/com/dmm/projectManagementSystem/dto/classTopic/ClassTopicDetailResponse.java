package com.dmm.projectManagementSystem.dto.classTopic;

import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.dmm.projectManagementSystem.dto.studentTopic.CUDStudentTopicRequest;
import com.dmm.projectManagementSystem.dto.studentTopic.StudentTopicResponse;
import com.dmm.projectManagementSystem.dto.topicSemester.CRUDTopicSemester;
import com.dmm.projectManagementSystem.dto.user.UserResponse;
import com.dmm.projectManagementSystem.model.ClassTopic;
import com.dmm.projectManagementSystem.model.TopicSemester;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClassTopicDetailResponse {
    Long id;

    String className;

    UserResponse teacher;

    LocalDateTime startRegistrationTime;

    LocalDateTime endRegistrationTime;

    CRUDTopicSemester topicSemester;

    CRUDMajor major;

    List<StudentTopicResponse> studentTopicList;

    static public ClassTopicDetailResponse fromClassTopic(
            ClassTopic classTopic,
            List<StudentTopicResponse> studentTopicList
    ){
        return ClassTopicDetailResponse.builder()
                .id(classTopic.getId())
                .className(classTopic.getClassName())
                .teacher(UserResponse.fromUser(classTopic.getTeacher()))
                .startRegistrationTime(classTopic.getStartRegistrationTime())
                .endRegistrationTime(classTopic.getEndRegistrationTime())
                .topicSemester(CRUDTopicSemester.fromTopicSemester(classTopic.getTopicSemester()))
                .major(CRUDMajor.fromMajor(classTopic.getMajor()))
                .studentTopicList(studentTopicList)
                .build();
    }
}
