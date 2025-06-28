package com.dmm.projectManagementSystem.controllers.student;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.announcement.AnnouncementStudentResDTO;
import com.dmm.projectManagementSystem.dto.group.res.AcceptInvitationResDTO;
import com.dmm.projectManagementSystem.dto.group.StudentTeamResDTO;
import com.dmm.projectManagementSystem.dto.group.res.UserTeamResDTO;
import com.dmm.projectManagementSystem.dto.team.InvitationDTO;
//import com.dmm.projectManagementSystem.service.student.teamService.EmailService;
import com.dmm.projectManagementSystem.dto.team.TeamInfoDTO;
import com.dmm.projectManagementSystem.model.User;
import com.dmm.projectManagementSystem.service.student.teamService.TeamServiceImpl;
import com.dmm.projectManagementSystem.utils.annotation.ApiMessageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("${api.prefix}/group")
public class TeamController {
    private final TeamServiceImpl teamServiceImpl;

    // private final EmailService emailService;
    private TeamController(TeamServiceImpl teamServiceImpl) {
        this.teamServiceImpl = teamServiceImpl;

    }

    @ApiMessageResponse(message = "Tạo nhóm làm đồ án")
    @GetMapping("/create_group")
    public ResponseEntity<ApiResponseStudent<StudentTeamResDTO>> createStudentGroup(@RequestParam Long studentId,
            @RequestParam String teamName) {
        ApiResponseStudent<StudentTeamResDTO> apiResponseStudent = new ApiResponseStudent<>();
        try {
            return ResponseEntity.ok(this.teamServiceImpl.handleCreateGroup(studentId, teamName));
        } catch (Exception e) {
            e.printStackTrace();
            apiResponseStudent.setMessage("Có lỗi xảy ra: " + e.getMessage());
            return ResponseEntity.badRequest().body(apiResponseStudent);
        }
    }

    @ApiMessageResponse(value = "Nhóm của bạn đã được tạo thành công và chờ duyệt từ giảng viên !")
    @PostMapping("/invite")
    public ResponseEntity<ApiResponseStudent<List<UserTeamResDTO>>> inviteMember(@RequestParam Long leaderId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(this.teamServiceImpl.inviteMember(leaderId, userId));
    }

    @ApiMessageResponse("Bạn chấp nhận tham gia nhóm thành công !")
    @PutMapping("/accept")
    public ResponseEntity<ApiResponseStudent<AcceptInvitationResDTO>> acceptInvitation(@RequestParam Long leaderId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(this.teamServiceImpl.handleAcceptJoinTeam(leaderId, userId));
    }

    @ApiMessageResponse("Từ chối tham gia nhóm ")
    @PutMapping("/decline")
    public ResponseEntity<ApiResponseStudent<Void>> rejectInvitation(@RequestParam Long leaderId,
            @RequestParam Long memberId) {
        return ResponseEntity.ok(this.teamServiceImpl.handleRejectJoinTeam(leaderId, memberId));
    }

    @ApiMessageResponse("Rời khỏi nhóm")
    @PutMapping("/remove")
    public ResponseEntity<ApiResponseStudent<Void>> removeStudentFromTeam(@RequestParam Long leaderId,
            @RequestParam Long memberId,
            @RequestParam Long teamId) {
        return ResponseEntity.ok(this.teamServiceImpl.handleRemoveStudentFromGroup(leaderId, memberId, teamId));
    }

    @ApiMessageResponse(message = "Hủy nhóm sinh viên")
    @DeleteMapping("/delete_team")
    public ResponseEntity<ApiResponseStudent<Void>> deleteTeam(
            @RequestParam Long leaderId) {
        return ResponseEntity.ok(this.teamServiceImpl.handleDeleteGroup(leaderId));
    }

    @ApiMessageResponse(message = "Lấy ra danh sách sinh viên")
    @GetMapping("/list")
    public ResponseEntity<ApiResponseStudent<List<Map<String, Object>>>> getAllStudents(Pageable pageable) {
        return ResponseEntity.ok(teamServiceImpl.handleGetListUser(pageable));
    }

    @ApiMessageResponse(message = "Lấy thông tin nhóm của sinh viên")
    @GetMapping("/info")
    public ResponseEntity<ApiResponseStudent<TeamInfoDTO>> getTeamInfo(@RequestParam Long studentId) {
        return ResponseEntity.ok(teamServiceImpl.getTeamInfo(studentId));
    }

    @ApiMessageResponse(message = "Lấy thông tin thành viên đươc mời vào nhóm")
    @GetMapping("/pending_members")
    public ResponseEntity<ApiResponseStudent<TeamInfoDTO>> getPendingMembers(@RequestParam Long studentId) {
        return ResponseEntity.ok(teamServiceImpl.getPendingMembersInfoByStudentId(studentId));
    }

    @ApiMessageResponse(message = "Lấy danh sách lời mời tham gia nhóm của sinh viên")
    @GetMapping("/invitations")
    public ResponseEntity<ApiResponseStudent<List<InvitationDTO>>> getInvitations(@RequestParam Long studentId) {
        return ResponseEntity.ok(teamServiceImpl.getInvitations(studentId));
    }

    @ApiMessageResponse(message = "Lấy danh sách thông báo của sinh viên")
    @GetMapping("/announcements")
    public ResponseEntity<ApiResponseStudent<List<AnnouncementStudentResDTO>>> getAnnouncements(
            @RequestParam Long studentId) {
        return ResponseEntity.ok(teamServiceImpl.getAnnouncementsByStudentId(studentId));
    }

    @ApiMessageResponse(message = "Lấy tất cả sinh viên trong StudentTopic")
    @GetMapping("/student-topic/all")
    public ResponseEntity<ApiResponseStudent<List<com.dmm.projectManagementSystem.dto.studentTopic.StudentTopicFullUserDTO>>> getAllStudentsInStudentTopic(
            org.springframework.data.domain.Pageable pageable) {
        return ResponseEntity.ok(teamServiceImpl.getAllStudentsInStudentTopic(pageable));
    }

    @ApiMessageResponse(message = "Kiểm tra trạng thái nhóm của sinh viên")
    @GetMapping("/check-team-status")
    public ResponseEntity<ApiResponseStudent<String>> checkStudentTeamStatus(@RequestParam Long studentId) {
        return ResponseEntity.ok(teamServiceImpl.checkStudentTeamStatus(studentId));
    }

}
