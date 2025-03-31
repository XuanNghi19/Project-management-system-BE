package com.dmm.projectManagementSystem.controllers.student;

import com.dmm.projectManagementSystem.dto.RestResponse;
import com.dmm.projectManagementSystem.dto.group.StudentTeamResDTO;
import com.dmm.projectManagementSystem.model.TeamMember;
import com.dmm.projectManagementSystem.service.student.teamService.TeamServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
public class TeamController {
    private final TeamServiceImpl teamServiceImpl;
    private TeamController(TeamServiceImpl teamServiceImpl) {
        this.teamServiceImpl = teamServiceImpl;
    }

    @PostMapping("/create_group")
    public ResponseEntity<RestResponse<StudentTeamResDTO>> createStudentGroup (@RequestParam Long studentId, @RequestParam String groupName) {
        return ResponseEntity.ok(this.teamServiceImpl.handleCreateGroup(studentId, groupName));
    }

    @GetMapping("/invite")
    public ResponseEntity<RestResponse<TeamMember>> inviteMember(@RequestParam Long leaderId, @RequestParam Long userId) {
        return ResponseEntity.ok(this.teamServiceImpl.inviteMember(leaderId, userId));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<String> acceptInvitation (@PathVariable ("id") Long idUser) {
        this.teamServiceImpl.handleAcceptJoinTeam(idUser);
       return ResponseEntity.ok("Đã chấp nhận tham gia nhóm !");
    }

    @PutMapping("/{id}/decline")
    public ResponseEntity<String> rejectInvitation(@PathVariable Long invitationId) {
        return ResponseEntity.ok("Đã từ chối lời mời !");
    }

    @DeleteMapping("/delete_team/{id}")
    public ResponseEntity<String> deleteTeam (@PathVariable ("id") Long teamId) {
        return ResponseEntity.ok("Chờ duyệt từ giảng viên");
    }



}
