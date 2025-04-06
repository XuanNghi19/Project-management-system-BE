package com.dmm.projectManagementSystem.controllers.student;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.group.res.AcceptInvitationResDTO;
import com.dmm.projectManagementSystem.dto.group.StudentTeamResDTO;
import com.dmm.projectManagementSystem.dto.group.res.UserTeamResDTO;
import com.dmm.projectManagementSystem.service.student.teamService.TeamServiceImpl;
import com.dmm.projectManagementSystem.utils.annotation.ApiMessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class TeamController {
    private final TeamServiceImpl teamServiceImpl;
    private TeamController(TeamServiceImpl teamServiceImpl) {
        this.teamServiceImpl = teamServiceImpl;
    }
    @ApiMessageResponse(message = "Tạo nhóm làm đồ án")
    @PostMapping("/create_group")
    public ResponseEntity<ApiResponseStudent<StudentTeamResDTO>> createStudentGroup (@RequestParam Long studentId,
                                                                                     @RequestParam String teamName) {

        return ResponseEntity.ok(this.teamServiceImpl.handleCreateGroup(studentId, teamName));
    }
    @ApiMessageResponse(value = "Nhóm của bạn đã được tạo thành công và chờ duyệt từ giảng viên !")
    @PostMapping("/invite")
    public ResponseEntity<List<UserTeamResDTO>> inviteMember(@RequestParam Long leaderId,
                                                             @RequestParam Long userId,
                                                             @RequestParam Long teamId) {
        return ResponseEntity.ok(this.teamServiceImpl.inviteMember(leaderId, userId, teamId));
    }
    @ApiMessageResponse("Bạn chấp nhận tham gia nhóm thành công !")
    @PutMapping("/accept")
    public ResponseEntity<ApiResponseStudent<AcceptInvitationResDTO>> acceptInvitation (@RequestParam Long leaderId,
                                                                                        @RequestParam Long userId,
                                                                                        @RequestParam Long teamId) {
       return ResponseEntity.ok(this.teamServiceImpl.handleAcceptJoinTeam(leaderId, userId, teamId));
    }
    @ApiMessageResponse("Từ chối tham gia nhóm ")
    @PutMapping("/decline")
    public ResponseEntity<ApiResponseStudent<Void>> rejectInvitation(@RequestParam Long leaderId,
                                                                     @RequestParam Long memberId,
                                                                     @RequestParam Long teamId) {
        return ResponseEntity.ok( this.teamServiceImpl.handleRejectJoinTeam(leaderId, memberId, teamId));
    }

    @ApiMessageResponse("Rời khỏi nhóm")
    @PutMapping("/remove")
    public ResponseEntity<ApiResponseStudent<Void>> removeStudentFromTeam(@RequestParam Long leaderId,
                                                                          @RequestParam Long memberId,
                                                                          @RequestParam Long teamId) {
        return ResponseEntity.ok( this.teamServiceImpl.handleRemoveStudentFromGroup(leaderId, memberId, teamId));
    }
    @ApiMessageResponse(message = "Hủy nhóm sinh viên")
    @DeleteMapping("/delete_team")
    public ResponseEntity<ApiResponseStudent<Void>> deleteTeam (
                                            @RequestParam Long leaderId,
                                            @RequestParam Long teamId) {
        return ResponseEntity.ok(this.teamServiceImpl.handleDeleteGroup(leaderId, teamId));
    }

}
