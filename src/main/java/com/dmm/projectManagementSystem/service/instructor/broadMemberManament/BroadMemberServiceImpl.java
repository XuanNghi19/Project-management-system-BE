package com.dmm.projectManagementSystem.service.instructor.broadMemberManament;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.boardMember.BoardMemberDTO;
import com.dmm.projectManagementSystem.model.BoardMember;
import com.dmm.projectManagementSystem.repo.BoardMemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BroadMemberServiceImpl implements BroadMemberService {
    @Autowired
    private BoardMemberRepo boardMemberRepo;
    @Override
    public ApiResponse<List<BoardMemberDTO>> getListBoardMember(Long id) {
        List<BoardMember> list=boardMemberRepo.findByUserId(id);
        List<BoardMemberDTO> res=list.stream().map(x->new BoardMemberDTO(x.getId(),x.getCouncil())).toList();
        return new ApiResponse<>(1000,"thành công",res);
    }
}
