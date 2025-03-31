package com.dmm.projectManagementSystem.dto.group;

import com.dmm.projectManagementSystem.model.TeamMember;
import com.dmm.projectManagementSystem.model.TopicSemester;
import com.dmm.projectManagementSystem.model.User;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentTeamResDTO {
    private String groupName;
    private User teacher;
    private TopicSemester topicSemester;
}
