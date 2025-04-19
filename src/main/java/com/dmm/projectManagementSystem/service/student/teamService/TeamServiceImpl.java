package com.dmm.projectManagementSystem.service.student.teamService;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.group.res.AcceptInvitationResDTO;
import com.dmm.projectManagementSystem.dto.group.StudentTeamResDTO;
import com.dmm.projectManagementSystem.dto.group.res.TeacherTeamResDTO;
import com.dmm.projectManagementSystem.dto.group.res.UserTeamResDTO;
import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.enums.MembershipPosition;
import com.dmm.projectManagementSystem.model.*;
import com.dmm.projectManagementSystem.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
     private final UserRepo userRepo;
     private final TeamRepo groupRepo;
     private final TopicRepo topicRepo;
     private final TeamMemberRepo teamMemberRepo;
     private final StudentTopicRepo studentTopicRepo;
     private final ClassTopicRepo classTopicRepo;
     private final TeamRepo teamRepo;


    // Trưởng nhóm tạo nhóm
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseStudent<StudentTeamResDTO> handleCreateGroup(Long leaderId, String teamName) {
        Optional<StudentTopic> studentTopic = studentTopicRepo.findByStudentId(leaderId);
        if (!studentTopic.isPresent()) {
            throw new IllegalStateException("Sinh viên chưa được phân vào lớp của giảng viên!");
        }
        ApiResponseStudent<StudentTeamResDTO> apiResponseStudent = new ApiResponseStudent<>();
        Optional<User> leader = userRepo.findById(leaderId);
        leader.orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng trong csdl !"));
        //Kiểm tra thời gian cho phép tạo nhóm
        ClassTopic classTopic = studentTopic.get().getClassTopic();
        Long teacherId = classTopic.getTeacher().getId();
        Optional<User> teacher = this.userRepo.findById(teacherId);
        Optional<List<TeamMember>> teamMember = teamMemberRepo.findByStudentId(leaderId);
        Team team  = new Team();
        User user = teacher.orElseThrow(() -> new NoSuchElementException("Không tìm thấy giảng viên!"));
       if (!teamMember.isPresent() || checkJoinTeam(teamMember.get())){
           team.setTeacher(teacher.get());
           team.setTopic(null);
           team.setTopicSemester(classTopic.getTopicSemester());
           team.setStatus(ProjectStage.PENDING);
           team.setTeamName(teamName);
           Team teamSaved = groupRepo.save(team);
       }else {
           apiResponseStudent.setMessage("Bạn đã tham gia nhóm rồi, không được đăng ký thêm nhóm !");
           return apiResponseStudent;
       }
        TopicSemester topicSemester = team.getTopicSemester();
        TeacherTeamResDTO teacherTeamResDTO = TeacherTeamResDTO.loadFromTeacherRes(user);
       TeamMember teamStudent = TeamMember.builder()
               .student(leader.get())
               .team(team)
               .position(MembershipPosition.LEADER)
               .build();
        teamMemberRepo.save(teamStudent);
        StudentTeamResDTO studentTeamResDTO = new StudentTeamResDTO();
        studentTeamResDTO.setGroupName(team.getTeamName());
        studentTeamResDTO.setTeacher(teacherTeamResDTO);
        studentTeamResDTO.setTopicSemester(topicSemester);
        apiResponseStudent.setData(studentTeamResDTO);
        apiResponseStudent.setMessage("Tạo nhóm đồ án thành công");

        return apiResponseStudent;
    }

    // chưa tối ưu hóa
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResponseStudent<List<UserTeamResDTO>> inviteMember (Long leaderId, Long memberId, Long teamId) {
        User memberDB = this.userRepo.findById(memberId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy thành viên cần mời trong csdl !"));
        StudentTopic studentTopic = studentTopicRepo.findByStudentId(memberId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy sinh viên trong lớp chủ đề được phân"));
        if (studentTopic.isStatus()){
            System.out.println("Người dùng đã tham gia nhóm !");
        }
        StudentTopic studentTopicLeader = studentTopicRepo.findByStudentId(leaderId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy sinh viên trong lớp chủ đề được phân"));
        studentTopic.setStatus(true);
        this.studentTopicRepo.save(studentTopicLeader);
        Optional<Team> team = this.teamRepo.findById(teamId);
        team.orElseThrow(() -> new NoSuchElementException("Không tìm thấy nhóm sinh viên"));

        TeamMember teamMember = TeamMember.builder()
                .student(memberDB)
                .team(team.get())
                .position(MembershipPosition.MEMBER)
                .build();

        this.teamMemberRepo.save(teamMember);
        List<TeamMember> teamMembers = this.teamMemberRepo.findByTeamId(team.get().getId());

        List<Long> listMembersId = teamMembers.stream().map(student -> student.getStudent().getId()).toList();
        Map<Long, User> userMap = userRepo.findByIdIn(listMembersId)
                .stream().collect(Collectors.toMap(User::getId, user -> user));
        ApiResponseStudent<List<UserTeamResDTO>> apiResponseInvite = new ApiResponseStudent<>();
        apiResponseInvite.setMessage("Mời tham gia nhóm thánh công !");
        apiResponseInvite.setData(UserTeamResDTO.fromUserTeamRes(teamMembers, userMap, false));
        return apiResponseInvite;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseStudent<AcceptInvitationResDTO> handleAcceptJoinTeam (Long leaderId, Long idUser, Long teamId) {
        Optional<StudentTopic> studentTopicDB = this.studentTopicRepo.findByStudentId(idUser);
        studentTopicDB.orElseThrow(() -> new NoSuchElementException("Không tìm thấy sinh viên trong lớp được phân công !"));
        studentTopicDB.get().setStatus(true);
        this.studentTopicRepo.save(studentTopicDB.get());
        Optional<Team> team = this.teamRepo.findById(teamId);
        team.orElseThrow(() -> new NoSuchElementException("Không tìm thấy thông tin về nhóm trong csdl !"));
        List<TeamMember> teamMembers = team.get().getListStudent();

        Optional<User> leader = this.userRepo.findById(leaderId);
        leader.orElseThrow(() -> new NoSuchElementException("Không tìm thấy thông tin nhóm trưởng"));

        List<Long> studentIds = teamMembers.stream().map(tm -> tm.getStudent().getId()).toList();
        Map<Long, User> userMap = userRepo.findByIdIn(studentIds)
                .stream().collect(Collectors.toMap(User::getId, user -> user));

        List<UserTeamResDTO> userTeamResDTO = UserTeamResDTO.fromUserTeamRes(teamMembers, userMap, studentTopicDB.get().isStatus());
        AcceptInvitationResDTO acceptInvitationResDTO = new AcceptInvitationResDTO();
        acceptInvitationResDTO.setStatus(true);
        acceptInvitationResDTO.setGroupName(team.get().getTeamName());
        acceptInvitationResDTO.setListMember(userTeamResDTO);

        ApiResponseStudent<AcceptInvitationResDTO> apiResponseAcceptTeam = new ApiResponseStudent<>();
        apiResponseAcceptTeam.setData(acceptInvitationResDTO);

        return apiResponseAcceptTeam;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseStudent<Void> handleRejectJoinTeam (Long leaderId, Long memberId, Long teamId) {
        ApiResponseStudent<Void> apiResponseStudent = new ApiResponseStudent<>();

        Optional<TeamMember> teamMember = this.teamMemberRepo.findByStudentIdAndTeamId(memberId, teamId);
        if (teamMember.isEmpty()) {
            apiResponseStudent.setMessage("Không tìm thấy thành viên trong nhóm!");
            return apiResponseStudent;
        }
        String message = this.teamMemberRepo.deleteByStudentId(memberId) > 0
                ? "Từ chối tham gia nhóm thành công"
                : "Từ chối tham gia nhóm không thành công";
        apiResponseStudent.setMessage(message);
        return apiResponseStudent;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseStudent<Void> handleRemoveStudentFromGroup(Long leaderId, Long memberId, Long teamId) {
        // thành viên rời nhóm thì cập nhật lại thông tin nhóm thành viên và thông báo rời nhóm thành công, cập nhật thông báo tới các thành viên khác
        ApiResponseStudent<Void> apiResponseStudent = new ApiResponseStudent<>();
        Optional<Team> team = teamRepo.findById(teamId);
        if (team.isEmpty()) {
            apiResponseStudent.setMessage("Nhóm không tồn tại trong CSDL !");
            return apiResponseStudent;
        }
        if (team.get().getStatus() != ProjectStage.PENDING) {
            apiResponseStudent.setMessage("Nhóm đã được duyệt, không thể xóa thành viên!");
            return apiResponseStudent;
        }
        Optional<TeamMember> teamMember = teamMemberRepo.findByStudentIdAndTeamId(memberId, teamId);
        if (teamMember.isEmpty()) {
            apiResponseStudent.setMessage("Không tìm thấy thành viên trong nhóm!");
            return apiResponseStudent;
        }
        this.teamMemberRepo.deleteByStudentId(memberId);
        apiResponseStudent.setMessage("Từ chối tham gia nhóm thành công!");
        return apiResponseStudent;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseStudent<Void> handleDeleteGroup(Long leaderId, Long teamId) {
        ApiResponseStudent<Void> apiResponseStudent = new ApiResponseStudent<>();
        Optional<Team> team = teamRepo.findById(teamId);
        if (team.isEmpty()) {
            apiResponseStudent.setMessage("Nhóm không tồn tại trong CSDL !");
            return apiResponseStudent;
        }
        if (team.get().getStatus() != ProjectStage.PENDING) {
            apiResponseStudent.setMessage("Nhóm đã được duyệt, không thể hủy nhóm!");
            return apiResponseStudent;
        }
        TeamMember leaderTeam = this.teamMemberRepo.findByStudentIdAndTeamId(leaderId, teamId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy trưởng nhóm trong bảng thành viên !"));
        if (leaderTeam.getPosition() != MembershipPosition.LEADER){
            apiResponseStudent.setMessage("Bạn không phải trưởng nhóm, hủy nhóm không thành công !");
            return apiResponseStudent;
        }

        List<TeamMember> listMember = this.teamMemberRepo.findByTeamId(teamId);
        for (TeamMember member : listMember){
            StudentTopic studentTopic = this.studentTopicRepo.findByStudentId(member.getStudent().getId()).orElseThrow(() -> new NoSuchElementException("Không tìm thấy sinh viên trong lớp được giao !"));
            if (studentTopic.isStatus()){
                studentTopic.setStatus(false);
                this.studentTopicRepo.save(studentTopic);
            }
        }

        this.teamMemberRepo.deleteByTeamId(teamId);
       this.teamRepo.deleteById(teamId);

        if (listMember.isEmpty()) {
            apiResponseStudent.setMessage("Không tìm thấy thành viên trong nhóm!");
            return apiResponseStudent;
        }
        apiResponseStudent.setMessage("Hủy nhóm thành công !");
        return apiResponseStudent;
    }

    public boolean canRegisterGroup(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(startTime) && now.isBefore(endTime);
    }

    private boolean checkJoinTeam (List<TeamMember> teamMembers) {
        for(TeamMember teamStudent : teamMembers){
           Topic topic = topicRepo.findById(teamStudent.getTeam().getId()).orElseThrow(() -> new NoSuchElementException("Lỗi tìm nhóm sinh viên đã tham gia !"));
           if (topic.getProjectStage() != ProjectStage.DEFENSE){
               return false;
           }
        }
        return true;
    }

}



