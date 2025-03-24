package com.dmm.projectManagementSystem.service.student.teamService;

import com.dmm.projectManagementSystem.enums.TeamStatus;
import com.dmm.projectManagementSystem.enums.MembershipPosition;
import com.dmm.projectManagementSystem.model.*;
import com.dmm.projectManagementSystem.repo.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TeamRepo groupRepo;
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private TeamMemberRepo teamMemberRepo;
    @Autowired
    private StudentTopicRepo studentTopicRepo;
    @Autowired
    private ClassTopicRepo classTopicRepo;

    // Trưởng nhóm tạo nhóm
    @Transactional
    @Override
    public boolean handleCreateGroup(Long leaderId, String teamName) {
        Optional<User> leader = userRepo.findById(leaderId);
        leader.orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng trong csdl !"));
        //Kiểm tra thời gian cho phép tạo nhóm
        if (this.teamMemberRepo.findByStudentId(leader.get().getId()).isPresent()){
            System.out.println("Bạn đã có nhóm rồi, không thể tạo được nhóm mới !");
            return false;
        }
        Optional<StudentTopic> studentTopic = studentTopicRepo.findByStudentId(leader.get().getId());
        if (!studentTopic.isPresent()){
            System.out.println("Sinh viên chưa được phân vào lớp của giảng viên !");
            return false;
        }
        Optional<ClassTopic> classTopic = studentTopicRepo.findByClassTopicId(studentTopic.get().getId());
        Optional<User> teacher = classTopicRepo.findByTeacherId(classTopic.get().getId());
        Team team  = new Team();
        team.setTeacher(teacher.get());
        team.setTopic(null);
        team.setStatus(TeamStatus.PENDING);
        team.setGroupName(teamName);
        Team teamSaved = groupRepo.save(team);

       TeamMember teamStudent = TeamMember.builder()
               .student(leader.get())
               .team(team)
               .position(MembershipPosition.LEADER)
               .build();
        teamMemberRepo.save(teamStudent);
    return true;
    }

    @Transactional
    public boolean inviteMember (Long leaderId, Long memberId) {
        User memberDB = this.userRepo.findById(memberId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy thành viên cần mời trong csdl !"));
        StudentTopic studentTopic = studentTopicRepo.findByStudentId(memberId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy sinh viên trong lớp chủ đề được phân"));
        // Có thể bỏ
        if (studentTopic.isStatus()){
            System.out.println("Người dùng đã tham gia nhóm !");
        }
        Optional<TeamMember> teamMemberInvited = this.teamMemberRepo.findByStudentId(leaderId);
        teamMemberInvited.orElseThrow(() -> new NoSuchElementException("Không tìm được nhóm được mời vào"));
        Team team = teamMemberInvited.get().getTeam();
        TeamMember teamMember = TeamMember.builder()
                .student(memberDB)
                .team(team)
                .position(MembershipPosition.MEMBER)
                .build();
        this.teamMemberRepo.save(teamMember);
        return true;

    }

    @Transactional
    public boolean handleAcceptJoinTeam (Long idUser) {
        Optional<StudentTopic> studentTopic = this.studentTopicRepo.findByStudentId(idUser);
        studentTopic.orElseThrow(() -> new NoSuchElementException("Không tìm thấy sinh viên trong lớp !"));
        studentTopic.get().setStatus(true);
        this.studentTopicRepo.save(studentTopic.get());
        return true;
    }
    // được dùng cho cả trường hợp leader hủy mời hoặc là người được mời từ chối tham gia
    @Transactional
    public boolean handleRejectJoinTeam (Long invitationId) {
        Optional<TeamMember> teamMember = this.teamMemberRepo.findById(invitationId);
        if (!teamMember.isPresent()){
            System.out.println("Không tìm thấy lời  mời !");
            return false;
        }
        this.teamMemberRepo.delete(teamMember.get());
        return true;
    }
    @Override
    public boolean handleRemoveStudentFromGroup() {
        return false;
    }

    @Override
    public boolean handleDeleteGroup(Long teamId) {

        return false;
    }

    public boolean canRegisterGroup(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(startTime) && now.isBefore(endTime);
    }

}



