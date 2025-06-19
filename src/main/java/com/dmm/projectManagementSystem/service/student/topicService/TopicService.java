package com.dmm.projectManagementSystem.service.student.topicService;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.topic.res.ReportResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicRegisterResDTO;
import com.dmm.projectManagementSystem.dto.topic.res.TopicResDTO;

public interface TopicService {
    // public boolean handleRegisterTopic(Long leaderId, String topicName);
    // public boolean handleUpdateTopic(Long id);
    // public Topic handleGetTopic(Long id);
    // List<Topic> handleGetAllTopic (Long userId);

    // Đăng ký đề tài theo nhóm (cần có team trước)
    ApiResponseStudent<TopicRegisterResDTO> handleRegisterTopic(Long leaderId, String topicName, String uri);

    // Đăng ký đề tài cá nhân (không cần team)
    ApiResponseStudent<TopicRegisterResDTO> handleRegisterIndividualTopic(Long studentId, String topicName, String uri);

    // Cập nhật đề tài (cả nhóm và cá nhân)
    ApiResponseStudent<TopicRegisterResDTO> handleUpdateTopic(Long studentId, String topicNameChange, String uri);

    // Báo cáo tiến độ
    ApiResponseStudent<ReportResDTO> handleAddFilesUrl(Long topicId, String uri);

    // Lấy thông tin đề tài
    ApiResponseStudent<TopicResDTO> handleGetTopic(Long studentId);

    // Kiểm tra xem sinh viên đã đăng ký đề tài chưa
    boolean hasRegisteredTopic(Long studentId);

    // Kiểm tra và xử lý nhóm 1 người
    ApiResponseStudent<String> checkAndHandleSinglePersonTeam(Long leaderId);

    // Kiểm tra trạng thái sinh viên và hướng dẫn API phù hợp
    ApiResponseStudent<String> checkStudentStatus(Long studentId);
}
