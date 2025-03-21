package com.dmm.projectManagementSystem.dto.group;

import com.dmm.projectManagementSystem.model.Group;
import com.dmm.projectManagementSystem.model.GroupStudent;
import com.dmm.projectManagementSystem.model.Topic;
import com.dmm.projectManagementSystem.model.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class GroupResponse {
    Long id;
    User teacher;
    String groupName;

    List<GroupStudentResponse> groupStudentResponseList;

    static public GroupResponse fromGroup(
            Group group,
            List<GroupStudent> groupStudentList
    ) {
        return GroupResponse.builder()
                .id(group.getId())
                .teacher(group.getTeacher())
                .groupName(group.getGroupName())
                .groupStudentResponseList(groupStudentList.stream().map(GroupStudentResponse::fromGroupStudent).toList())
                .build();
    }
}
