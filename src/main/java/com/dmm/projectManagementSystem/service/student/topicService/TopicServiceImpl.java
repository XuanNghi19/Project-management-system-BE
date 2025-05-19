package com.dmm.projectManagementSystem.service.student.topicService;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.topic.res.ReportResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicRegisterResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicResDTO;
import com.dmm.projectManagementSystem.enums.MembershipPosition;
import com.dmm.projectManagementSystem.enums.ProjectStage;
import com.dmm.projectManagementSystem.model.*;
import com.dmm.projectManagementSystem.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseStudent<TopicRegisterResDTO> handleRegisterTopic(Long leaderId, String topicName, String uri) {
        // Lấy Team từ leaderId
        Team team = teamMemberRepo.findFirstByStudentId(leaderId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy nhóm của bạn!"))
                .getTeam();

        // Kiểm tra người này có thực sự thuộc nhóm và là trưởng nhóm không
        Optional<TeamMember> leaderTeam = teamMemberRepo.findByStudentIdAndTeamId(leaderId, team.getId());
        leaderTeam.orElseThrow(() -> new NoSuchElementException("Người dùng chưa tạo nhóm để đăng ký đề tài!"));

        MembershipPosition positionInTeam = leaderTeam.get().getPosition();
        ApiResponseStudent<TopicRegisterResDTO> apiResponseStudent = new ApiResponseStudent<>();

        if (positionInTeam != MembershipPosition.LEADER) {
            apiResponseStudent.setMessage("Không phải là trưởng nhóm nên không đăng ký được đề tài!");
            return apiResponseStudent;
        }

        // Tạo đề tài mới
        Topic topicRegister = new Topic();
        topicRegister.setName(topicName);
        topicRegister.setProjectStage(ProjectStage.IDEATION);
        topicRegister.setTopicSemester(team.getTopicSemester());

        // Tạo file đính kèm (nếu có)
        FilesUrl filesUrl = new FilesUrl();
        filesUrl.setTopic(topicRegister);
        filesUrl.setProjectStage(ProjectStage.IDEATION);
        filesUrl.setUri(uri);
        filesUrlRepo.save(filesUrl);

        // Gán topic cho nhóm
        team.setTopic(topicRegister);
        teamRepo.save(team);

        // Trả về kết quả
        TopicRegisterResDTO topicRegisterResDTO = TopicRegisterResDTO.fromTopicRes(topicRegister, filesUrl, team);
        topicRepo.save(topicRegister);
        apiResponseStudent.setData(topicRegisterResDTO);
        return apiResponseStudent;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseStudent<TopicRegisterResDTO> handleUpdateTopic(Long leaderId, Long topicId, String topicNameChange, String uri) {
        Team teamStudent = teamRepo.findByTopicId(topicId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy nhóm sinh viên đăng ký"));
        Long studentId = teamStudent.getId();
        Optional<TeamMember> leaderTeam = teamMemberRepo.findByStudentIdAndTeamId(leaderId, studentId);
        leaderTeam.orElseThrow(() -> new NoSuchElementException("Lỗi không đăng ký được  !"));
        ApiResponseStudent<TopicRegisterResDTO> apiResponseStudent = new ApiResponseStudent<>();
        MembershipPosition positionInTeam = leaderTeam.get().getPosition();
        if (positionInTeam != MembershipPosition.LEADER){
            apiResponseStudent.setMessage("Không phải là trưởng nhóm nên không đăng ký được đề tài !");
            return apiResponseStudent;
        }
        Topic topic = topicRepo.findById(topicId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy đề tài trong CSDL !"));

        if (topicNameChange != null && !topicNameChange.isBlank() && !topicNameChange.equals(topic.getName())) {
            topic.setName(topicNameChange);
            topicRepo.save(topic);
        }
        FilesUrl updateFilesUrl = filesUrlRepo.findByTopicId(topic.getId()).orElseThrow(() -> new NoSuchElementException("Không tìm thấy file trong giai đoạn này của chủ đề !"));
        if (uri != null && !uri.isBlank() && !uri.equals(updateFilesUrl.getUri())) {
            updateFilesUrl.setUri(uri);
            filesUrlRepo.save(updateFilesUrl);
        }
        TopicRegisterResDTO topicUpdated = TopicRegisterResDTO.fromTopicResWithoutTeam(topic, updateFilesUrl);
        apiResponseStudent.setData(topicUpdated);
        apiResponseStudent.setMessage("Cập nhật đề tài thành công !");
        return apiResponseStudent;
    }

    // có cần phải thêm người nộp cho phần này
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseStudent<ReportResDTO> handleAddFilesUrl (Long topicId, String uri) {
        Topic topicDB = topicRepo.findById(topicId).orElseThrow(() -> new NoSuchElementException("Không tìm được đề tài nhóm đăng ký !"));
        FilesUrl filesUrl = FilesUrl.builder()
                .projectStage(topicDB.getProjectStage())
                .topic(topicDB)
                .uri(uri)
                .build();
        filesUrlRepo.save(filesUrl);
        ReportResDTO reportResDTO = ReportResDTO.fromReportRes(filesUrl);
        ApiResponseStudent<ReportResDTO> reportResponse = new ApiResponseStudent<>();
        reportResponse.setData(reportResDTO);
        return reportResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseStudent<TopicResDTO> handleGetTopic(Long studentId) {
        // Tìm TeamMember dựa vào studentId
        TeamMember teamMember = teamMemberRepo.findFirstByStudentId(studentId)
                .orElseThrow(() -> new NoSuchElementException("Sinh viên chưa tham gia nhóm nào!"));

        // Lấy nhóm mà sinh viên thuộc về
        Team teamStudent = teamMember.getTeam();

        // Kiểm tra xem nhóm có đăng ký đề tài hay chưa
        Topic topicFound = teamStudent.getTopic();
        if (topicFound == null) {
            throw new NoSuchElementException("Nhóm của sinh viên chưa đăng ký đề tài nào!");
        }

        // Tạo response
        ApiResponseStudent<TopicResDTO> apiResponse = new ApiResponseStudent<>();
        TopicResDTO topicResDTO = TopicResDTO.loadFromTopicRes(topicFound, teamStudent);
        apiResponse.setData(topicResDTO);
        apiResponse.setMessage("Lấy thông tin đề tài đăng ký thành công !");
        return apiResponse;
    }



    public boolean canRegisterTopic(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(startTime) && now.isBefore(endTime);
    }
}
