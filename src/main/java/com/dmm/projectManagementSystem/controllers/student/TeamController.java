package com.dmm.projectManagementSystem.controllers.student;

import com.dmm.projectManagementSystem.service.student.teamService.TeamServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invitations")
public class TeamController {
    private final TeamServiceImpl teamServiceImpl;
    private TeamController(TeamServiceImpl teamServiceImpl) {
        this.teamServiceImpl = teamServiceImpl;
    }

    @PostMapping("/create_group")
    public ResponseEntity<String> createStudentGroup (@RequestParam Long studentId, String groupName) {
        this.teamServiceImpl.handleCreateGroup(studentId, groupName);
        return ResponseEntity.ok("Tạo nhóm thành công !");
    }



    @GetMapping("/invite")
    public ResponseEntity<String> inviteMember(@RequestParam Long leaderId, @RequestParam Long userId) {
        this.teamServiceImpl.inviteMember(leaderId, userId);
        return ResponseEntity.ok("Mời thành công");
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
