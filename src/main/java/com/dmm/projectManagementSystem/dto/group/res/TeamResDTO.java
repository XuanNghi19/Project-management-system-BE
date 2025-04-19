package com.dmm.projectManagementSystem.dto.group.res;

import com.dmm.projectManagementSystem.enums.TeamStatus;
import com.dmm.projectManagementSystem.model.TopicSemester;
import com.dmm.projectManagementSystem.model.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.List;

public class TeamResDTO {
    private List<UserTeamResDTO> listStudent;
    private User teacher;

    private String groupName;

    @Enumerated(EnumType.STRING)
    private TeamStatus status;

    private TopicSemester topicSemester;

}
