package com.dmm.projectManagementSystem.dto.group;

import com.dmm.projectManagementSystem.dto.group.res.TeacherTeamResDTO;
import com.dmm.projectManagementSystem.model.TopicSemester;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentTeamResDTO {
    private String groupName;
    private TeacherTeamResDTO teacher;
    private TopicSemester topicSemester;
}
