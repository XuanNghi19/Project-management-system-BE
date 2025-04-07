package com.dmm.projectManagementSystem.service.admin.councilManagement;

import com.dmm.projectManagementSystem.dto.boardMember.CRUDBoardMember;
import com.dmm.projectManagementSystem.model.*;
import com.dmm.projectManagementSystem.repo.BoardMemberRepo;
import com.dmm.projectManagementSystem.repo.CouncilRepo;
import com.dmm.projectManagementSystem.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardMemberServiceImpl implements BoardMemberService {

    final private BoardMemberRepo boardMemberRepo;
    final private UserRepo userRepo;
    final private CouncilRepo councilRepo;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void addBoardMember(Council council, CRUDBoardMember request) throws Exception {
        try {
            User exUser = userRepo.findByIdNum(request.getUser().getIdNum())
                    .orElseThrow(Exception::new);

            BoardMember boardMember = BoardMember.fromCRUDBoardMember(request, council, exUser);
            boardMember.setId(null);
            boardMemberRepo.save(boardMember);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void updateBoardMember(CRUDBoardMember request) throws Exception {
        try {
            BoardMember exBoardMember = boardMemberRepo.findById(request.getId())
                    .orElseThrow(Exception::new);

            User exUser = userRepo.findByIdNum(request.getUser().getIdNum())
                    .orElseThrow(Exception::new);

            BoardMember boardMember = BoardMember.fromCRUDBoardMember(request, exBoardMember.getCouncil(), exUser);
            boardMemberRepo.save(boardMember);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void deleteBoardMember(Long boardMemberID) throws Exception {
        if (boardMemberRepo.existsById(boardMemberID)) {
            try {
                boardMemberRepo.deleteById(boardMemberID);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }
}
