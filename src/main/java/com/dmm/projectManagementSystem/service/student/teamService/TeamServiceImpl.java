package com.dmm.projectManagementSystem.service.student.teamService;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.Metadata;
import com.dmm.projectManagementSystem.dto.announcement.AnnouncementStudentResDTO;
import com.dmm.projectManagementSystem.dto.group.res.AcceptInvitationResDTO;
import com.dmm.projectManagementSystem.dto.group.StudentTeamResDTO;
import com.dmm.projectManagementSystem.dto.group.res.TeacherTeamResDTO;
import com.dmm.projectManagementSystem.dto.group.res.UserTeamResDTO;
import com.dmm.projectManagementSystem.dto.team.InvitationDTO;
import com.dmm.projectManagementSystem.dto.team.MemberDTO;
import com.dmm.projectManagementSystem.dto.team.TeamInfoDTO;
import com.dmm.projectManagementSystem.dto.topic.res.FileInfoDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicFilesResDTO;
import com.dmm.projectManagementSystem.dto.user.UserResponse;
import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.enums.MembershipPosition;
import com.dmm.projectManagementSystem.enums.Role;
import com.dmm.projectManagementSystem.enums.TeamStatus;
import com.dmm.projectManagementSystem.model.*;
import com.dmm.projectManagementSystem.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Meta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashMap;
import com.dmm.projectManagementSystem.dto.studentTopic.StudentTopicFullUserDTO;

@Service
public class TeamServiceImpl implements TeamService {
        private final UserRepo userRepo;
        private final TopicRepo topicRepo;
        private final TeamMemberRepo teamMemberRepo;
        private final StudentTopicRepo studentTopicRepo;
        private final ClassTopicRepo classTopicRepo;
        private final TeamRepo teamRepo;
        private final AnnouncementRepo announcementRepo;
        private final FilesUrlRepo filesUrlRepo;

        public TeamServiceImpl(UserRepo userRepo, TopicRepo topicRepo, TeamMemberRepo teamMemberRepo,
                        StudentTopicRepo studentTopicRepo, ClassTopicRepo classTopicRepo, TeamRepo teamRepo,
                        AnnouncementRepo announcementRepo, FilesUrlRepo filesUrlRepo) {
                this.userRepo = userRepo;
                this.topicRepo = topicRepo;
                this.teamMemberRepo = teamMemberRepo;
                this.studentTopicRepo = studentTopicRepo;
                this.classTopicRepo = classTopicRepo;
                this.teamRepo = teamRepo;
                this.announcementRepo = announcementRepo;
                this.filesUrlRepo = filesUrlRepo;
        }

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
                // Kiểm tra thời gian cho phép tạo nhóm
                ClassTopic classTopic = studentTopic.get().getClassTopic();
                Long teacherId = classTopic.getTeacher().getId();
                Optional<User> teacher = this.userRepo.findById(teacherId);
                Optional<List<TeamMember>> teamMember = teamMemberRepo.findByStudentId(leaderId);
                Team team = new Team();
                User user = teacher.orElseThrow(() -> new NoSuchElementException("Không tìm thấy giảng viên!"));
                team.setTeacher(teacher.get());
                team.setTopic(null);
                team.setTopicSemester(classTopic.getTopicSemester());
                team.setMajor(leader.get().getMajor());
                team.setStatus(ProjectStage.PENDING);
                team.setGroupName(teamName);
                Team teamSaved = teamRepo.save(team);
                TopicSemester topicSemester = team.getTopicSemester();
                TeacherTeamResDTO teacherTeamResDTO = TeacherTeamResDTO.loadFromTeacherRes(user);
                TeamMember teamStudent = TeamMember.builder()
                                .student(leader.get())
                                .team(team)
                                .position(MembershipPosition.LEADER)
                                .build();
                teamMemberRepo.save(teamStudent);
                StudentTeamResDTO studentTeamResDTO = new StudentTeamResDTO();
                studentTeamResDTO.setGroupName(team.getGroupName());
                studentTeamResDTO.setTeacher(teacherTeamResDTO);
                studentTeamResDTO.setTopicSemester(topicSemester);
                apiResponseStudent.setData(studentTeamResDTO);
                apiResponseStudent.setMessage("Tạo nhóm đồ án thành công");

                return apiResponseStudent;
        }

        // chưa tối ưu hóa
        @Transactional(rollbackFor = Exception.class)
        @Override
        public ApiResponseStudent<List<UserTeamResDTO>> inviteMember(Long leaderId, Long memberId) {
                User memberDB = this.userRepo.findById(memberId)
                                .orElseThrow(() -> new NoSuchElementException(
                                                "Không tìm thấy thành viên cần mời trong csdl !"));
                StudentTopic studentTopic = studentTopicRepo.findByStudentId(memberId)
                                .orElseThrow(() -> new NoSuchElementException(
                                                "Không tìm thấy sinh viên trong lớp chủ đề được phân"));
                ApiResponseStudent<List<UserTeamResDTO>> apiResponseInvite = new ApiResponseStudent<>();
                if (studentTopic.isStatus()) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Sinh viên này đã tham gia nhóm khác!");
                }
                StudentTopic studentTopicLeader = studentTopicRepo.findByStudentId(leaderId)
                                .orElseThrow(() -> new NoSuchElementException(
                                                "Không tìm thấy sinh viên trong lớp chủ đề được phân"));
                // Lấy team từ leaderId
                TeamMember leaderTeamMember = this.teamMemberRepo.findFirstByStudentId(leaderId)
                                .orElseThrow(() -> new NoSuchElementException(
                                                "Không tìm thấy trưởng nhóm trong bảng thành viên !"));
                Team team = leaderTeamMember.getTeam();

                // Kiểm tra vai trò của người mời
                if (leaderTeamMember.getPosition() != MembershipPosition.LEADER) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                                        "Chỉ trưởng nhóm mới có quyền mời thành viên!");
                }

                // Kiểm tra thành viên đã được mời hoặc đã ở trong nhóm chưa
                Optional<TeamMember> existingMemberOpt = teamMemberRepo.findFirstByStudentIdAndTeamId(memberId,
                                team.getId());
                if (existingMemberOpt.isPresent()) {
                        TeamMember existingMember = existingMemberOpt.get();
                        if (existingMember.getStatus() == TeamStatus.DECLINED) {
                                // Cho phép mời lại: cập nhật trạng thái thành PENDING
                                existingMember.setStatus(TeamStatus.PENDING);
                                teamMemberRepo.save(existingMember);
                        } else {
                                throw new ResponseStatusException(HttpStatus.CONFLICT,
                                                "Thành viên này đã được mời hoặc đã ở trong nhóm!");
                        }
                } else {
                        // Tạo mới như cũ
                        TeamMember teamMember = TeamMember.builder()
                                        .student(memberDB)
                                        .team(team)
                                        .position(MembershipPosition.MEMBER)
                                        .status(TeamStatus.PENDING)
                                        .build();
                        this.teamMemberRepo.save(teamMember);
                }

                // Thêm thông báo vào bảng Announcement
                Announcement announcement = Announcement.builder()
                                .title("Lời mời tham gia nhóm: " + team.getGroupName())
                                .content("Bạn được mời tham gia nhóm '" + team.getGroupName() + "' bởi trưởng nhóm "
                                                + leaderTeamMember.getStudent().getName()
                                                + ". Hãy vào hệ thống để chấp nhận hoặc từ chối lời mời.")
                                .datePosted(java.time.LocalDateTime.now().toString())
                                .projectStage(team.getStatus())
                                .team(team)
                                .build();
                // Giả sử bạn có announcementRepo là một bean đã được inject
                announcementRepo.save(announcement);

                List<TeamMember> teamMembers = this.teamMemberRepo.findByTeamId(team.getId());

                List<Long> listMembersId = teamMembers.stream().map(student -> student.getStudent().getId()).toList();
                Map<Long, User> userMap = userRepo.findByIdIn(listMembersId)
                                .stream().collect(Collectors.toMap(User::getId, user -> user));
                apiResponseInvite.setMessage("Mời tham gia nhóm thành công !");
                apiResponseInvite.setData(UserTeamResDTO.fromUserTeamRes(teamMembers, userMap, false));
                return apiResponseInvite;
        }

        @Transactional(rollbackFor = Exception.class)
        public ApiResponseStudent<AcceptInvitationResDTO> handleAcceptJoinTeam(Long leaderId, Long idUser) {
                Optional<StudentTopic> studentTopicDB = this.studentTopicRepo.findByStudentId(idUser);
                studentTopicDB
                                .orElseThrow(() -> new NoSuchElementException(
                                                "Không tìm thấy sinh viên trong lớp được phân công !"));
                studentTopicDB.get().setStatus(true);
                this.studentTopicRepo.save(studentTopicDB.get());
                // Tìm team từ leaderId
                TeamMember leaderTeamMember = this.teamMemberRepo.findFirstByStudentId(leaderId)
                                .orElseThrow(() -> new NoSuchElementException(
                                                "Không tìm thấy trưởng nhóm trong bảng thành viên !"));
                if (leaderTeamMember.getPosition() != MembershipPosition.LEADER) {
                        throw new NoSuchElementException("Người này không phải trưởng nhóm!");
                }
                Team team = leaderTeamMember.getTeam();
                Long teamId = team.getId();
                List<TeamMember> teamMembers = team.getListStudent();

                Optional<User> leader = this.userRepo.findById(leaderId);
                leader.orElseThrow(() -> new NoSuchElementException("Không tìm thấy thông tin nhóm trưởng"));

                List<Long> studentIds = teamMembers.stream().map(tm -> tm.getStudent().getId()).toList();
                Map<Long, User> userMap = userRepo.findByIdIn(studentIds)
                                .stream().collect(Collectors.toMap(User::getId, user -> user));

                List<UserTeamResDTO> userTeamResDTO = UserTeamResDTO.fromUserTeamRes(teamMembers, userMap,
                                studentTopicDB.get().isStatus());
                AcceptInvitationResDTO acceptInvitationResDTO = new AcceptInvitationResDTO();
                acceptInvitationResDTO.setStatus(true);
                acceptInvitationResDTO.setGroupName(team.getGroupName());
                acceptInvitationResDTO.setListMember(userTeamResDTO);

                Optional<TeamMember> teamMemberOpt = teamMemberRepo.findFirstByStudentIdAndTeamId(idUser, teamId);
                if (teamMemberOpt.isEmpty()) {
                        System.err.println("Không tìm thấy thành viên trong nhóm với studentId=" + idUser + ", teamId="
                                        + teamId);
                        ApiResponseStudent<AcceptInvitationResDTO> response = new ApiResponseStudent<>();
                        response.setMessage("Không tìm thấy thành viên trong nhóm!");
                        return response;
                }
                TeamMember teamMember = teamMemberOpt.get();
                teamMember.setStatus(TeamStatus.ACCEPTED);
                teamMemberRepo.save(teamMember);

                // Thêm thông báo vào bảng Announcement khi sinh viên accept
                User user = userRepo.findById(idUser)
                                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy user!"));
                Announcement announcement = Announcement.builder()
                                .title("Thành viên mới đã tham gia nhóm")
                                .content("Sinh viên '" + user.getName() + "' đã chấp nhận tham gia nhóm '"
                                                + team.getGroupName()
                                                + "'.")
                                .datePosted(java.time.LocalDateTime.now().toString())
                                .projectStage(team.getStatus())
                                .team(team)
                                .build();
                announcementRepo.save(announcement);

                ApiResponseStudent<AcceptInvitationResDTO> apiResponseAcceptTeam = new ApiResponseStudent<>();
                apiResponseAcceptTeam.setData(acceptInvitationResDTO);

                return apiResponseAcceptTeam;
        }

        @Transactional(rollbackFor = Exception.class)
        public ApiResponseStudent<Void> handleRejectJoinTeam(Long leaderId, Long memberId) {
                ApiResponseStudent<Void> apiResponseStudent = new ApiResponseStudent<>();
                // Tìm team từ leaderId
                TeamMember leaderTeamMember = this.teamMemberRepo.findFirstByStudentId(leaderId)
                                .orElseThrow(() -> new NoSuchElementException(
                                                "Không tìm thấy trưởng nhóm trong bảng thành viên !"));
                if (leaderTeamMember.getPosition() != MembershipPosition.LEADER) {
                        apiResponseStudent.setMessage("Người này không phải trưởng nhóm!");
                        return apiResponseStudent;
                }
                Team team = leaderTeamMember.getTeam();
                Long teamId = team.getId();

                Optional<TeamMember> teamMemberOpt = this.teamMemberRepo.findFirstByStudentIdAndTeamId(memberId,
                                teamId);
                if (teamMemberOpt.isEmpty()) {
                        apiResponseStudent.setMessage("Không tìm thấy thành viên trong nhóm!");
                        return apiResponseStudent;
                }
                TeamMember teamMember = teamMemberOpt.get();
                teamMember.setStatus(TeamStatus.DECLINED);
                teamMemberRepo.save(teamMember);
                apiResponseStudent.setMessage("Từ chối tham gia nhóm thành công");

                // Thêm thông báo vào bảng Announcement khi sinh viên từ chối
                User user = userRepo.findById(memberId).orElse(null);
                Announcement announcement = Announcement.builder()
                                .title("Thành viên đã từ chối tham gia nhóm")
                                .content("Sinh viên '" + (user != null ? user.getName() : "")
                                                + "' đã từ chối tham gia nhóm '"
                                                + team.getGroupName() + "'.")
                                .datePosted(java.time.LocalDateTime.now().toString())
                                .projectStage(team.getStatus())
                                .team(team)
                                .build();
                announcementRepo.save(announcement);

                return apiResponseStudent;
        }

        @Override
        @Transactional(rollbackFor = Exception.class)
        public ApiResponseStudent<Void> handleRemoveStudentFromGroup(Long leaderId, Long memberId, Long teamId) {
                // thành viên rời nhóm thì cập nhật lại thông tin nhóm thành viên và thông báo
                // rời nhóm thành công, cập nhật thông báo tới các thành viên khác
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
                if (team.get().getTopic() != null) {
                        throw new org.springframework.web.server.ResponseStatusException(
                                        org.springframework.http.HttpStatus.BAD_REQUEST,
                                        "Nhóm đã tham gia đề tài, không thể xóa thành viên!");
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
        public ApiResponseStudent<Void> handleDeleteGroup(Long leaderId) {
                ApiResponseStudent<Void> apiResponseStudent = new ApiResponseStudent<>();
                // Tìm team từ leaderId
                TeamMember leaderTeamMember = this.teamMemberRepo.findFirstByStudentId(leaderId)
                                .orElseThrow(() -> new NoSuchElementException(
                                                "Không tìm thấy trưởng nhóm trong bảng thành viên !"));
                Team team = leaderTeamMember.getTeam();
                if (leaderTeamMember.getPosition() != MembershipPosition.LEADER) {
                        apiResponseStudent.setMessage("Bạn không phải trưởng nhóm, hủy nhóm không thành công !");
                        return apiResponseStudent;
                }
                if (team.getStatus() != ProjectStage.PENDING) {
                        apiResponseStudent.setMessage("Nhóm đã được duyệt, không thể hủy nhóm!");
                        return apiResponseStudent;
                }
                Long teamId = team.getId();
                // Xóa tất cả announcement liên quan đến team
                announcementRepo.deleteAllByTeam(team);
                // Nếu team có topic, xóa liên kết topic.team
                if (team.getTopic() != null) {
                        Topic topic = team.getTopic();
                        topic.setTeam(null);
                        topicRepo.save(topic);
                }
                List<TeamMember> listMember = this.teamMemberRepo.findByTeamId(teamId);
                for (TeamMember member : listMember) {
                        StudentTopic studentTopic = this.studentTopicRepo.findByStudentId(member.getStudent().getId())
                                        .orElseThrow(() -> new NoSuchElementException(
                                                        "Không tìm thấy sinh viên trong lớp được giao !"));
                        if (studentTopic.isStatus()) {
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

        @Override
        public ApiResponseStudent<TeamInfoDTO> getTeamInfo(Long studentId) {
                TeamMember teamMember = teamMemberRepo.findFirstByStudentId(studentId)
                                .orElseThrow(() -> new NoSuchElementException("Sinh viên chưa tham gia nhóm nào"));

                Team team = teamMember.getTeam();
                List<TeamMember> teamMembers = teamMemberRepo.findByTeamId(team.getId());

                // Lọc: luôn lấy leader, member thì chỉ lấy nếu ACCEPTED
                List<TeamMember> filteredMembers = teamMembers.stream()
                                .filter(tm -> tm.getPosition() == MembershipPosition.LEADER
                                                || (tm.getPosition() == MembershipPosition.MEMBER
                                                                && tm.getStatus() == TeamStatus.ACCEPTED))
                                .collect(Collectors.toList());

                List<Long> userIds = filteredMembers.stream()
                                .map(tm -> tm.getStudent().getId())
                                .collect(Collectors.toList());

                Map<Long, User> userMap = userRepo.findByIdIn(userIds)
                                .stream()
                                .collect(Collectors.toMap(User::getId, user -> user));

                List<MemberDTO> memberList = filteredMembers.stream().map(tm -> {
                        User user = userMap.get(tm.getStudent().getId());
                        return MemberDTO.builder()
                                        .id(user.getId())
                                        .name(user.getName())
                                        .email(user.getEmail())
                                        .position(tm.getPosition().name())
                                        .build();
                }).collect(Collectors.toList());

                TeamInfoDTO teamInfo = TeamInfoDTO.builder()
                                .teamId(team.getId())
                                .teamName(team.getGroupName())
                                .members(memberList)
                                .build();

                ApiResponseStudent<TeamInfoDTO> response = new ApiResponseStudent<>();
                response.setMessage("Lấy thông tin nhóm thành công");
                response.setData(teamInfo);
                return response;
        }

        public ApiResponseStudent<List<Map<String, Object>>> handleGetListUser(Pageable pageable) {
                Page<User> listStudent = userRepo.findAllStudent(Role.STUDENT, null, null, null, pageable);

                Metadata metadata = new Metadata();
                metadata.setPage(pageable.getPageNumber());
                metadata.setPageSize(pageable.getPageSize());
                metadata.setTotalPage(listStudent.getTotalPages());
                metadata.setTotalElement(listStudent.getTotalElements());

                List<Map<String, Object>> result = new java.util.ArrayList<>();
                for (User user : listStudent.getContent()) {
                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("user", user);
                        // Lấy status từ StudentTopic
                        StudentTopic studentTopic = studentTopicRepo.findByStudentId(user.getId()).orElse(null);
                        userMap.put("status", studentTopic != null ? studentTopic.isStatus() : null);
                        result.add(userMap);
                }

                ApiResponseStudent<List<Map<String, Object>>> apiResponseGetStudent = new ApiResponseStudent<>();
                apiResponseGetStudent.setMetadata(metadata);
                apiResponseGetStudent.setMessage("Lấy danh sách sinh viên thành công !");
                apiResponseGetStudent.setData(result);
                return apiResponseGetStudent;
        }

        public boolean canRegisterGroup(LocalDateTime startTime, LocalDateTime endTime) {
                LocalDateTime now = LocalDateTime.now();
                return now.isAfter(startTime) && now.isBefore(endTime);
        }

        private boolean checkJoinTeam(List<TeamMember> teamMembers) {
                for (TeamMember teamStudent : teamMembers) {
                        Topic topic = topicRepo.findById(teamStudent.getTeam().getId())
                                        .orElseThrow(() -> new NoSuchElementException(
                                                        "Lỗi tìm nhóm sinh viên đã tham gia !"));
                        if (topic.getProjectStage() != ProjectStage.DEFENSE) {
                                return false;
                        }
                }
                return true;
        }

        public ApiResponseStudent<List<MemberDTO>> getPendingMembers(Long teamId) {
                List<TeamMember> pendingMembers = teamMemberRepo.findByTeamIdAndStatus(teamId, TeamStatus.PENDING);
                List<Long> userIds = pendingMembers.stream()
                                .map(tm -> tm.getStudent().getId())
                                .collect(Collectors.toList());
                Map<Long, User> userMap = userRepo.findByIdIn(userIds)
                                .stream()
                                .collect(Collectors.toMap(User::getId, user -> user));
                List<MemberDTO> memberList = pendingMembers.stream().map(tm -> {
                        User user = userMap.get(tm.getStudent().getId());
                        return MemberDTO.builder()
                                        .id(user.getId())
                                        .name(user.getName())
                                        .email(user.getEmail())
                                        .position(tm.getPosition().name())
                                        .build();
                }).collect(Collectors.toList());

                ApiResponseStudent<List<MemberDTO>> response = new ApiResponseStudent<>();
                response.setMessage("Lấy danh sách sinh viên pending thành công");
                response.setData(memberList);
                return response;
        }

        public ApiResponseStudent<TeamInfoDTO> getPendingMembersInfo(Long teamId) {
                Team team = teamRepo.findById(teamId)
                                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy nhóm!"));

                // Lấy các thành viên PENDING
                List<TeamMember> pendingMembers = teamMemberRepo.findByTeamIdAndStatus(teamId, TeamStatus.PENDING);
                List<Long> userIds = pendingMembers.stream()
                                .map(tm -> tm.getStudent().getId())
                                .collect(Collectors.toList());
                Map<Long, User> userMap = userRepo.findByIdIn(userIds)
                                .stream()
                                .collect(Collectors.toMap(User::getId, user -> user));
                List<com.dmm.projectManagementSystem.dto.team.MemberDTO> memberList = pendingMembers.stream()
                                .map(tm -> {
                                        User user = userMap.get(tm.getStudent().getId());
                                        return com.dmm.projectManagementSystem.dto.team.MemberDTO.builder()
                                                        .id(user.getId())
                                                        .name(user.getName())
                                                        .email(user.getEmail())
                                                        .position(tm.getPosition().name())
                                                        .build();
                                }).collect(Collectors.toList());

                TeamInfoDTO teamInfo = TeamInfoDTO.builder()
                                .teamId(team.getId())
                                .teamName(team.getGroupName())
                                .members(memberList)
                                .build();

                ApiResponseStudent<TeamInfoDTO> response = new ApiResponseStudent<>();
                response.setMessage("Lấy danh sách thành viên pending thành công");
                response.setData(teamInfo);
                return response;
        }

        public ApiResponseStudent<TeamInfoDTO> getPendingMembersInfoByStudentId(Long studentId) {
                // Tìm teamId từ studentId
                TeamMember teamMember = teamMemberRepo.findFirstByStudentId(studentId)
                                .orElseThrow(() -> new NoSuchElementException("Sinh viên chưa tham gia nhóm nào"));
                Long teamId = teamMember.getTeam().getId();
                return getPendingMembersInfo(teamId);
        }

        @Override
        public ApiResponseStudent<List<InvitationDTO>> getInvitations(
                        Long studentId) {
                List<TeamMember> invitations = teamMemberRepo.findByStudentIdAndStatus(studentId,
                                com.dmm.projectManagementSystem.enums.TeamStatus.PENDING);
                List<com.dmm.projectManagementSystem.dto.team.InvitationDTO> result = invitations.stream().map(tm -> {
                        Team team = tm.getTeam();
                        TeamMember leader = teamMemberRepo.findByTeamIdAndPosition(team.getId(),
                                        MembershipPosition.LEADER);
                        return InvitationDTO.builder()
                                        .teamId(team.getId())
                                        .teamName(team.getGroupName())
                                        .leaderId(leader != null ? leader.getStudent().getId() : null)
                                        .leaderName(leader != null ? leader.getStudent().getName() : null)
                                        .build();
                }).toList();
                ApiResponseStudent<List<com.dmm.projectManagementSystem.dto.team.InvitationDTO>> response = new ApiResponseStudent<>();
                response.setData(result);
                response.setMessage("Lấy danh sách lời mời thành công");
                return response;
        }

        public ApiResponseStudent<List<com.dmm.projectManagementSystem.dto.announcement.AnnouncementStudentResDTO>> getAnnouncementsByStudentId(
                        Long studentId) {
                // Lấy tất cả các teamId mà sinh viên là thành viên hoặc được mời
                List<TeamMember> teamMembers = teamMemberRepo.findByStudentId(studentId).orElse(List.of());
                List<Long> teamIds = teamMembers.stream().map(tm -> tm.getTeam().getId()).distinct().toList();
                List<Announcement> announcements = teamIds.stream()
                                .flatMap(teamId -> announcementRepo
                                                .findAllByTeam(teamRepo.findById(teamId).orElse(null)).stream())
                                .toList();
                List<AnnouncementStudentResDTO> result = announcements.stream()
                                .map(a -> AnnouncementStudentResDTO.builder()
                                                .id(a.getId())
                                                .title(a.getTitle())
                                                .content(a.getContent())
                                                .datePosted(a.getDatePosted())
                                                .teamName(a.getTeam() != null ? a.getTeam().getGroupName() : null)
                                                .teamId(a.getTeam() != null ? a.getTeam().getId() : null)
                                                .projectStage(a.getProjectStage() != null ? a.getProjectStage().name()
                                                                : null)
                                                .build())
                                .toList();
                ApiResponseStudent<List<AnnouncementStudentResDTO>> response = new ApiResponseStudent<>();
                response.setData(result);
                response.setMessage("Lấy danh sách thông báo thành công");
                return response;
        }

        public ApiResponseStudent<List<StudentTopicFullUserDTO>> getAllStudentsInStudentTopic(Pageable pageable) {
                Page<StudentTopic> studentTopics = studentTopicRepo.findAll(pageable);
                List<StudentTopicFullUserDTO> result = studentTopics.getContent().stream().map(st -> {
                        User user = st.getStudent();
                        return StudentTopicFullUserDTO.builder()
                                        .id(user.getId())
                                        .idNum(user.getIdNum())
                                        .name(user.getName())
                                        .age(user.getAge())
                                        .dob(user.getDob())
                                        .cccd(user.getCccd())
                                        .email(user.getEmail())
                                        .phoneNumber(user.getPhoneNumber())
                                        .sex(user.getSex())
                                        .avatarUrl(user.getAvatarUrl())
                                        .address(user.getAddress())
                                        .active(user.isActive())
                                        .role(user.getRole() != null ? user.getRole().name() : null)
                                        .course(user.getCourse())
                                        .department(user.getDepartment())
                                        .major(user.getMajor())
                                        .status(st.isStatus())
                                        .build();
                }).toList();
                Metadata metadata = new Metadata();
                metadata.setPage(studentTopics.getNumber());
                metadata.setPageSize(studentTopics.getSize());
                metadata.setTotalPage(studentTopics.getTotalPages());
                metadata.setTotalElement(studentTopics.getTotalElements());
                ApiResponseStudent<List<StudentTopicFullUserDTO>> response = new ApiResponseStudent<>();
                response.setData(result);
                response.setMetadata(metadata);
                response.setMessage("Lấy danh sách sinh viên trong StudentTopic thành công!");
                return response;
        }

}
