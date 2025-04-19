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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    private final TopicRepo topicRepo;

    private final TeamRepo teamRepo;

    private TeamMemberRepo teamMemberRepo;

    private final FilesUrlRepo filesUrlRepo;

    private final ClassTopicRepo classTopicRepo;

    private final UserRepo userRepo;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseStudent<TopicRegisterResDTO> handleRegisterTopic(Long leaderId, Long teamId, String topicName, String uri) {
        // bổ sung thêm phần tìm theo nhiều điều kiện vd teamId, chứ không phải là có mỗi leaderId
        Optional<Team> team = teamRepo.findById(teamId);
        team.orElseThrow(() -> new NoSuchElementException("Không tìm thấy nhóm của bạn!"));

        Optional<TeamMember> leaderTeam = teamMemberRepo.findByStudentIdAndTeamId(leaderId, teamId);
        leaderTeam.orElseThrow(() -> new NoSuchElementException("Người dùng chưa tạo nhóm để đăng ký đề tài !"));
        MembershipPosition positionInTeam = leaderTeam.get().getPosition();
        ApiResponseStudent<TopicRegisterResDTO> apiResponseStudent = new ApiResponseStudent<>();
        if (positionInTeam != MembershipPosition.LEADER){
            apiResponseStudent.setMessage("Không phải là trưởng nhóm nên không đăng ký được đề tài !");
            return apiResponseStudent;
        }
          Topic topicRegister = new Topic();
            topicRegister.setName(topicName);
            topicRegister.setProjectStage(ProjectStage.IDEATION);
            topicRegister.setTopicSemester(team.get().getTopicSemester());

            FilesUrl filesUrl = new FilesUrl();
            filesUrl.setTopic(topicRegister);
            filesUrl.setProjectStage(ProjectStage.IDEATION);
            filesUrl.setUri(uri);
            filesUrlRepo.save(filesUrl);

            team.get().setTopic(topicRegister);
            teamRepo.save(team.get());

            TopicRegisterResDTO topicRegisterResDTO = TopicRegisterResDTO.fromTopicRes(topicRegister, filesUrl, team.get());
            topicRepo.save(topicRegister);
            apiResponseStudent.setData(topicRegisterResDTO);
            return apiResponseStudent;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponseStudent<TopicRegisterResDTO> handleUpdateTopic(Long leaderId, Long topicId, String topicNameChange, String uri) {
        Team teamStudent = teamRepo.findByTopicId(topicId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy nhóm sinh viên đăng ký"));
        Optional<TeamMember> leaderTeam = teamMemberRepo.findByStudentIdAndTeamId(leaderId, teamStudent.getId());
        leaderTeam.orElseThrow(() -> new NoSuchElementException("Người dùng chưa tạo nhóm để đăng ký đề tài !"));
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
    public ApiResponseStudent<TopicResDTO> handleGetTopic(Long topicId) {
       Topic topicFound = topicRepo.findById(topicId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy chủ đề trong CSDL !"));
       Team teamStudent = teamRepo.findByTopicId(topicId).orElseThrow(()-> new NoSuchElementException("Không tìm thấy nhóm sinh viên!"));
//       TeamMember teamMember = teamMemberRepo.findByStudentIdAndTeamId(userId, teamStudent.getId()).orElseThrow(() -> new NoSuchElementException("Không tìm thấy nhóm sinh viên đăng ký"));
       ApiResponseStudent<TopicResDTO> apiResponseGetTopic = new ApiResponseStudent<>();
        TopicResDTO topicResDTO = TopicResDTO.loadFromTopicRes(topicFound, teamStudent);
        apiResponseGetTopic.setData(topicResDTO);
        apiResponseGetTopic.setMessage("Lấy thông tin đề tài đăng ký thành công !");
        return apiResponseGetTopic;
    }

    public boolean canRegisterTopic(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(startTime) && now.isBefore(endTime);
    }
}
