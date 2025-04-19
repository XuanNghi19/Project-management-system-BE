package com.dmm.projectManagementSystem.service.instructor.teamManagement;

import com.dmm.projectManagementSystem.dto.ApiResponse;
import com.dmm.projectManagementSystem.dto.team.TeamDTO;
import com.dmm.projectManagementSystem.dto.team.UpdateTeam;
import com.dmm.projectManagementSystem.exception.InvalidException;
import com.dmm.projectManagementSystem.model.Team;
import com.dmm.projectManagementSystem.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService{
    @Autowired
    private TeamRepo teamRepo;
    @Override
    public ApiResponse<List<TeamDTO>> getListTeam(Long id) {
        List<Team> list=teamRepo.findByTeacher_Id(id);
        List<TeamDTO> res=list.stream().map(x->new TeamDTO(x.getId(),x.getGroupName(),x.getTopic().getName(),x.getStatus(),x.getTopicSemester(),x.getMajor(),x.getTopic().getGrade())).toList();
        return new ApiResponse<>(1000,"thanh cong",res);
    }

    @Override
    public ApiResponse<String> updateTeam(UpdateTeam updateTeam) {
        Optional<Team> team= Optional.of(teamRepo.findById(updateTeam.getId()).orElseThrow(()->new InvalidException("khong co team")));
        team.get().setStatus(updateTeam.getStatus());
        teamRepo.save(team.get());
        return new ApiResponse<>(1000,"Đã duyệt thành công",null);
    }
}
