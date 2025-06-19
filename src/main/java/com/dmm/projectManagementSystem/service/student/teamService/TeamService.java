package com.dmm.projectManagementSystem.service.student.teamService;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.RestResponse;
import com.dmm.projectManagementSystem.dto.group.StudentTeamResDTO;
import com.dmm.projectManagementSystem.dto.group.res.AcceptInvitationResDTO;
import com.dmm.projectManagementSystem.dto.group.res.UserTeamResDTO;
import com.dmm.projectManagementSystem.dto.team.TeamInfoDTO;
import com.dmm.projectManagementSystem.dto.team.InvitationDTO;
import com.dmm.projectManagementSystem.dto.announcement.AnnouncementResponse;
import com.dmm.projectManagementSystem.dto.announcement.AnnouncementStudentResDTO;

import java.util.List;

public interface TeamService {
    ApiResponseStudent<StudentTeamResDTO> handleCreateGroup(Long userID, String groupName);

    // public boolean handleRemoveStudentFromGroup();
    ApiResponseStudent<TeamInfoDTO> getTeamInfo(Long studentId);

    ApiResponseStudent<Void> handleDeleteGroup(Long leaderId, Long teamId);

    ApiResponseStudent<List<UserTeamResDTO>> inviteMember(Long leaderId, Long memberId);

    ApiResponseStudent<AcceptInvitationResDTO> handleAcceptJoinTeam(Long leaderId, Long idUser, Long teamId);

    ApiResponseStudent<Void> handleRejectJoinTeam(Long leaderId, Long memberId, Long teamId);

    ApiResponseStudent<Void> handleRemoveStudentFromGroup(Long leaderId, Long memberId, Long teamId);

    ApiResponseStudent<List<InvitationDTO>> getInvitations(Long studentId);

    ApiResponseStudent<List<AnnouncementStudentResDTO>> getAnnouncementsByStudentId(Long studentId);

}
