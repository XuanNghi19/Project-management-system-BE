package com.dmm.projectManagementSystem.controller.instructor;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.boardMember.BoardMemberDTO;
import com.dmm.projectManagementSystem.service.instructor.broadMemberManament.BroadMemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoardMemberController {
    @Autowired
    private BroadMemberServiceImpl broadMemberService;
    @GetMapping("/board_member/{id}")
    public ResponseEntity<ApiResponse<List<BoardMemberDTO>>> getBoardMember(@PathVariable("id") Long id){
        return ResponseEntity.ok(broadMemberService.getListBoardMember(id));
    }
}
