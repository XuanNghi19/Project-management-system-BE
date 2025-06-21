package com.dmm.projectManagementSystem.service.student.topicService;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.topic.res.ReportResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicRegisterResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicFilesResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.FileInfoDTO;
import com.dmm.projectManagementSystem.enums.MembershipPosition;
import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.enums.TopicType;
import com.dmm.projectManagementSystem.model.*;
import com.dmm.projectManagementSystem.repo.*;
import com.dmm.projectManagementSystem.service.serviceUtils.FirebaseService;
import com.dmm.projectManagementSystem.service.serviceUtils.LocalFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    @Autowired
    private final TopicRepo topicRepo;
    @Autowired
    private final TeamRepo teamRepo;
    @Autowired
    private final TeamMemberRepo teamMemberRepo;
    @Autowired
    private final FilesUrlRepo filesUrlRepo;
    @Autowired
    private final ClassTopicRepo classTopicRepo;
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final AnnouncementRepo announcementRepo;
    @Autowired
    private final StudentTopicRepo studentTopicRepo;
    @Autowired
    private final FirebaseService firebaseService;
    @Autowired
    private final LocalFileService localFileService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseStudent<TopicRegisterResDTO> handleRegisterTopic(Long leaderId, String topicName,
            MultipartFile file) {

        // Kiểm tra xem sinh viên đã đăng ký đề tài chưa
        if (hasRegisteredTopic(leaderId)) {
            ApiResponseStudent<TopicRegisterResDTO> response = new ApiResponseStudent<>();
            response.setMessage("Bạn đã đăng ký đề tài rồi!");
            return response;
        }

        // Lấy Team từ leaderId
        Optional<TeamMember> teamMemberOpt = teamMemberRepo.findFirstByStudentId(leaderId);
        if (teamMemberOpt.isEmpty()) {
            ApiResponseStudent<TopicRegisterResDTO> response = new ApiResponseStudent<>();
            response.setMessage(
                    "Bạn không thuộc nhóm nào! Vui lòng sử dụng API đăng ký đề tài cá nhân: POST /topic/register_individual_topic");
            return response;
        }

        Team team = teamMemberOpt.get().getTeam();

        // Kiểm tra người này có thực sự thuộc nhóm và là trưởng nhóm không
        Optional<TeamMember> leaderTeam = teamMemberRepo.findByStudentIdAndTeamId(leaderId, team.getId());
        leaderTeam.orElseThrow(() -> new NoSuchElementException("Người dùng chưa tạo nhóm để đăng ký đề tài!"));

        MembershipPosition positionInTeam = leaderTeam.get().getPosition();
        ApiResponseStudent<TopicRegisterResDTO> apiResponseStudent = new ApiResponseStudent<>();

        if (positionInTeam != MembershipPosition.LEADER) {
            apiResponseStudent.setMessage("Không phải là trưởng nhóm nên không đăng ký được đề tài!");
            return apiResponseStudent;
        }

        // Kiểm tra số lượng thành viên trong nhóm
        List<TeamMember> teamMembers = teamMemberRepo.findByTeamId(team.getId());
        if (teamMembers.size() == 1) {
            // Nếu nhóm chỉ có 1 người, chuyển thành đề tài cá nhân và xóa nhóm
            ApiResponseStudent<TopicRegisterResDTO> individualResponse = handleRegisterIndividualTopic(leaderId,
                    topicName, file);

            // Xóa nhóm 1 người sau khi đã tạo đề tài cá nhân thành công
            if (individualResponse.getData() != null) {
                // Xóa team member trước
                teamMemberRepo.deleteByTeamId(team.getId());
                // Xóa team
                teamRepo.deleteById(team.getId());

                // Cập nhật thông báo
                individualResponse.setMessage("Nhóm 1 người đã được chuyển thành đề tài cá nhân thành công!");
            }

            return individualResponse;
        }

        // Xử lý upload file
        String fileUrl = "";
        try {
            if (file != null && !file.isEmpty()) {
                fileUrl = localFileService.saveFile(file);
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu file local: " + e.getMessage(), e);
        }

        // Tạo đề tài mới cho nhóm
        Topic topicRegister = new Topic();
        topicRegister.setName(topicName);
        topicRegister.setProjectStage(ProjectStage.IDEATION);
        topicRegister.setTopicSemester(team.getTopicSemester());
        topicRegister.setTopicType(TopicType.TEAM);
        topicRegister.setTeam(team);
        topicRepo.save(topicRegister);

        // Tạo file đính kèm (nếu có)
        FilesUrl filesUrl = new FilesUrl();
        filesUrl.setTopic(topicRegister);
        filesUrl.setProjectStage(ProjectStage.IDEATION);
        filesUrl.setUri(fileUrl);
        filesUrlRepo.save(filesUrl);

        // Gán topic cho nhóm
        team.setTopic(topicRegister);
        teamRepo.save(team);

        // Thêm thông báo
        Announcement announcement = Announcement.builder()
                .title("Đề tài đã được đăng ký")
                .content("Nhóm '" + team.getGroupName() + "' đã đăng ký đề tài: '" + topicName + "'.")
                .datePosted(java.time.LocalDateTime.now().toString())
                .projectStage(topicRegister.getProjectStage())
                .team(team)
                .build();
        announcementRepo.save(announcement);

        // Trả về kết quả
        TopicRegisterResDTO topicRegisterResDTO = TopicRegisterResDTO.fromTopicRes(topicRegister, filesUrl, team);
        apiResponseStudent.setData(topicRegisterResDTO);
        return apiResponseStudent;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseStudent<TopicRegisterResDTO> handleRegisterIndividualTopic(Long studentId, String topicName,
            MultipartFile file) {
        // Kiểm tra xem sinh viên đã đăng ký đề tài chưa
        if (hasRegisteredTopic(studentId)) {
            ApiResponseStudent<TopicRegisterResDTO> response = new ApiResponseStudent<>();
            response.setMessage("Bạn đã đăng ký đề tài rồi!");
            return response;
        }

        // Lấy thông tin sinh viên
        User student = userRepo.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy sinh viên!"));

        // Lấy thông tin lớp học của sinh viên
        StudentTopic studentTopic = studentTopicRepo.findByStudentId(studentId)
                .orElseThrow(() -> new NoSuchElementException("Sinh viên chưa được phân vào lớp!"));

        // Xử lý upload file
        String fileUrl = "";
        try {
            if (file != null && !file.isEmpty()) {
                fileUrl = localFileService.saveFile(file);
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu file local: " + e.getMessage(), e);
        }

        // Tạo đề tài cá nhân
        Topic topicRegister = new Topic();
        topicRegister.setName(topicName);
        topicRegister.setProjectStage(ProjectStage.IDEATION);
        topicRegister.setTopicSemester(studentTopic.getClassTopic().getTopicSemester());
        topicRegister.setTopicType(TopicType.INDIVIDUAL);
        topicRegister.setIndividualStudent(student);
        topicRepo.save(topicRegister);

        // Tạo file đính kèm (nếu có)
        FilesUrl filesUrl = new FilesUrl();
        filesUrl.setTopic(topicRegister);
        filesUrl.setProjectStage(ProjectStage.IDEATION);
        filesUrl.setUri(fileUrl);
        filesUrlRepo.save(filesUrl);

        // Cập nhật trạng thái sinh viên
        studentTopic.setStatus(true);
        studentTopicRepo.save(studentTopic);

        // Thêm thông báo
        Announcement announcement = Announcement.builder()
                .title("Đề tài cá nhân đã được đăng ký")
                .content("Sinh viên '" + student.getName() + "' đã đăng ký đề tài cá nhân: '" + topicName + "'.")
                .datePosted(java.time.LocalDateTime.now().toString())
                .projectStage(topicRegister.getProjectStage())
                .build();
        announcementRepo.save(announcement);

        // Trả về kết quả
        TopicRegisterResDTO topicRegisterResDTO = TopicRegisterResDTO.fromIndividualTopicRes(topicRegister, filesUrl,
                student);
        ApiResponseStudent<TopicRegisterResDTO> apiResponseStudent = new ApiResponseStudent<>();
        apiResponseStudent.setData(topicRegisterResDTO);
        apiResponseStudent.setMessage("Đăng ký đề tài cá nhân thành công!");
        return apiResponseStudent;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseStudent<TopicRegisterResDTO> handleUpdateTopic(Long studentId, String topicNameChange) {
        // Tìm đề tài của sinh viên (có thể là nhóm hoặc cá nhân)
        Topic topic = findTopicByStudentId(studentId);
        if (topic == null) {
            throw new NoSuchElementException("Bạn chưa đăng ký đề tài nào!");
        }

        // Nếu là đề tài nhóm, chỉ cho phép trưởng nhóm cập nhật
        if (topic.getTopicType() == TopicType.TEAM) {
            // Kiểm tra vai trò
            Optional<TeamMember> teamMemberOpt = teamMemberRepo.findFirstByStudentId(studentId);
            if (teamMemberOpt.isEmpty() || teamMemberOpt.get().getPosition() != MembershipPosition.LEADER) {
                throw new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.FORBIDDEN,
                        "Chỉ trưởng nhóm mới được cập nhật đề tài nhóm!");
            }
        }

        boolean isTopicNameUpdated = false;

        // Cập nhật tên đề tài nếu có thay đổi
        if (topicNameChange != null && !topicNameChange.isBlank() && !topicNameChange.equals(topic.getName())) {
            topic.setName(topicNameChange);
            isTopicNameUpdated = true;
        }

        if (isTopicNameUpdated) {
            topicRepo.save(topic);

            // Create announcement
            StringBuilder announcementContent = new StringBuilder();
            if (topic.getTopicType() == TopicType.TEAM) {
                announcementContent.append("Trưởng nhóm đã cập nhật thông tin đề tài: ");
            } else {
                announcementContent.append("Sinh viên đã cập nhật thông tin đề tài: ");
            }

            if (isTopicNameUpdated) {
                announcementContent.append("Tên đề tài được thay đổi thành '").append(topic.getName()).append("'. ");
            }

            Announcement announcement = Announcement.builder()
                    .title("Cập nhật thông tin đề tài")
                    .content(announcementContent.toString())
                    .datePosted(java.time.LocalDateTime.now().toString())
                    .projectStage(topic.getProjectStage())
                    .team(topic.getTeam())
                    .build();
            announcementRepo.save(announcement);
        }

        TopicRegisterResDTO topicUpdated = TopicRegisterResDTO.fromTopicResWithoutTeam(topic, null);
        ApiResponseStudent<TopicRegisterResDTO> apiResponseStudent = new ApiResponseStudent<>();
        apiResponseStudent.setData(topicUpdated);
        apiResponseStudent.setMessage("Cập nhật đề tài thành công!");
        return apiResponseStudent;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseStudent<ReportResDTO> handleAddFilesUrl(Long studentId, MultipartFile file) {
        Topic topic = findTopicByStudentId(studentId);
        if (topic == null) {
            throw new NoSuchElementException("Không tìm thấy đề tài cho sinh viên này!");
        }
        User submitter = userRepo.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy sinh viên!"));
        String fileUrl = "";
        try {
            if (file != null && !file.isEmpty()) {
                fileUrl = localFileService.saveFile(file);
            } else {
                ApiResponseStudent<ReportResDTO> response = new ApiResponseStudent<>();
                response.setMessage("Tệp không được để trống.");
                return response;
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu file local: " + e.getMessage(), e);
        }

        FilesUrl filesUrl = FilesUrl.builder()
                .projectStage(topic.getProjectStage())
                .topic(topic)
                .uri(fileUrl)
                .submittedBy(submitter)
                .build();
        filesUrlRepo.save(filesUrl);

        // Thêm thông báo khi nộp file
        String announcementTitle = "Báo cáo tiến độ mới được nộp";
        String announcementContent;

        if (topic.getTopicType() == TopicType.TEAM) {
            announcementContent = "Nhóm '" + topic.getTeam().getGroupName() + "' đã nộp báo cáo tiến độ cho đề tài '"
                    + topic.getName() + "' (Giai đoạn: " + topic.getProjectStage().name() + ").";
        } else {
            announcementContent = "Sinh viên '" + submitter.getName() + "' đã nộp báo cáo tiến độ cho đề tài '"
                    + topic.getName() + "' (Giai đoạn: " + topic.getProjectStage().name() + ").";
        }

        Announcement announcement = Announcement.builder()
                .title(announcementTitle)
                .content(announcementContent)
                .datePosted(java.time.LocalDateTime.now().toString())
                .projectStage(topic.getProjectStage())
                .team(topic.getTeam())
                .build();
        announcementRepo.save(announcement);

        ReportResDTO reportResDTO = ReportResDTO.fromReportRes(filesUrl);
        ApiResponseStudent<ReportResDTO> reportResponse = new ApiResponseStudent<>();
        reportResponse.setData(reportResDTO);
        reportResponse.setMessage("Nộp báo cáo tiến độ thành công!");
        return reportResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseStudent<TopicResDTO> handleGetTopic(Long studentId) {
        Topic topic = findTopicByStudentId(studentId);
        if (topic == null) {
            throw new NoSuchElementException("Bạn chưa đăng ký đề tài nào!");
        }

        // Tạo response
        ApiResponseStudent<TopicResDTO> apiResponse = new ApiResponseStudent<>();
        TopicResDTO topicResDTO;
        if (topic.getTopicType() == TopicType.TEAM) {
            topicResDTO = TopicResDTO.loadFromTopicRes(topic, topic.getTeam());
        } else {
            topicResDTO = TopicResDTO.loadFromIndividualTopicRes(topic, topic.getIndividualStudent());
        }

        apiResponse.setData(topicResDTO);
        apiResponse.setMessage("Lấy thông tin đề tài đăng ký thành công!");
        return apiResponse;
    }

    @Override
    public boolean hasRegisteredTopic(Long studentId) {
        // Kiểm tra xem sinh viên đã đăng ký đề tài chưa (cả nhóm và cá nhân)
        Optional<TeamMember> teamMember = teamMemberRepo.findFirstByStudentId(studentId);
        if (teamMember.isPresent() && teamMember.get().getTeam().getTopic() != null) {
            return true; // Đã đăng ký đề tài nhóm
        }

        // Kiểm tra đề tài cá nhân
        Optional<Topic> individualTopic = topicRepo.findByIndividualStudentId(studentId);
        return individualTopic.isPresent();
    }

    private Topic findTopicByStudentId(Long studentId) {
        // Tìm đề tài nhóm trước
        Optional<TeamMember> teamMember = teamMemberRepo.findFirstByStudentId(studentId);
        if (teamMember.isPresent() && teamMember.get().getTeam().getTopic() != null) {
            return teamMember.get().getTeam().getTopic();
        }

        // Tìm đề tài cá nhân
        return topicRepo.findByIndividualStudentId(studentId).orElse(null);
    }

    @Override
    public ApiResponseStudent<String> checkAndHandleSinglePersonTeam(Long leaderId) {
        ApiResponseStudent<String> response = new ApiResponseStudent<>();

        try {
            // Tìm team từ leaderId
            TeamMember leaderTeamMember = teamMemberRepo.findFirstByStudentId(leaderId)
                    .orElseThrow(() -> new NoSuchElementException("Không tìm thấy nhóm của bạn!"));

            Team team = leaderTeamMember.getTeam();

            // Kiểm tra xem người này có phải là trưởng nhóm không
            if (leaderTeamMember.getPosition() != MembershipPosition.LEADER) {
                response.setMessage("Bạn không phải là trưởng nhóm!");
                return response;
            }

            // Kiểm tra số lượng thành viên
            List<TeamMember> teamMembers = teamMemberRepo.findByTeamId(team.getId());

            if (teamMembers.size() == 1) {
                // Đánh dấu nhóm là nhóm 1 người
                team.setIsSinglePerson(true);
                teamRepo.save(team);

                response.setMessage(
                        "Nhóm của bạn chỉ có 1 người. Khi đăng ký đề tài, nhóm sẽ được chuyển thành đề tài cá nhân.");
                response.setData("SINGLE_PERSON_TEAM");
            } else {
                response.setMessage("Nhóm của bạn có " + teamMembers.size()
                        + " thành viên. Có thể đăng ký đề tài nhóm bình thường.");
                response.setData("MULTI_PERSON_TEAM");
            }

        } catch (Exception e) {
            response.setMessage("Có lỗi xảy ra: " + e.getMessage());
        }

        return response;
    }

    @Override
    public ApiResponseStudent<String> checkStudentStatus(Long studentId) {
        ApiResponseStudent<String> response = new ApiResponseStudent<>();

        try {
            // Kiểm tra xem sinh viên đã đăng ký đề tài chưa
            if (hasRegisteredTopic(studentId)) {
                response.setMessage("Bạn đã đăng ký đề tài rồi!");
                response.setData("ALREADY_REGISTERED");
                return response;
            }

            // Kiểm tra xem sinh viên có thuộc nhóm nào không
            Optional<TeamMember> teamMember = teamMemberRepo.findFirstByStudentId(studentId);

            if (teamMember.isEmpty()) {
                response.setMessage(
                        "Bạn không thuộc nhóm nào. Vui lòng sử dụng API đăng ký đề tài cá nhân: POST /topic/register_individual_topic");
                response.setData("INDIVIDUAL_TOPIC");
                return response;
            }

            Team team = teamMember.get().getTeam();
            List<TeamMember> teamMembers = teamMemberRepo.findByTeamId(team.getId());

            if (teamMembers.size() == 1) {
                response.setMessage(
                        "Nhóm của bạn chỉ có 1 người. Khi đăng ký đề tài, nhóm sẽ được chuyển thành đề tài cá nhân. Vui lòng sử dụng API: POST /topic/register_topic");
                response.setData("SINGLE_PERSON_TEAM");
            } else {
                response.setMessage("Bạn thuộc nhóm '" + team.getGroupName() + "' với " + teamMembers.size()
                        + " thành viên. Vui lòng sử dụng API đăng ký đề tài nhóm: POST /topic/register_topic");
                response.setData("MULTI_PERSON_TEAM");
            }

        } catch (Exception e) {
            response.setMessage("Có lỗi xảy ra: " + e.getMessage());
            response.setData("ERROR");
        }

        return response;
    }

    public boolean canRegisterTopic(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(startTime) && now.isBefore(endTime);
    }

    public ApiResponseStudent<TopicFilesResDTO> getFilesOfTopicByStudentId(Long studentId) {
        Topic topic = findTopicByStudentId(studentId);
        if (topic == null) {
            throw new NoSuchElementException("Không tìm thấy đề tài cho sinh viên này!");
        }
        List<FilesUrl> files = filesUrlRepo.findAllByTopic(topic);
        List<FileInfoDTO> fileDtos = files.stream()
                .map(f -> FileInfoDTO.builder()
                        .id(f.getId())
                        .uri(f.getUri())
                        .projectStage(f.getProjectStage() != null ? f.getProjectStage().name() : null)
                        .submittedByName(f.getSubmittedBy() != null ? f.getSubmittedBy().getName() : null)
                        .build())
                .toList();
        TopicFilesResDTO dto = TopicFilesResDTO.from(topic, fileDtos);
        ApiResponseStudent<TopicFilesResDTO> response = new ApiResponseStudent<>();
        response.setData(dto);
        response.setMessage("Lấy danh sách file và thông tin đề tài thành công!");
        return response;
    }
}
