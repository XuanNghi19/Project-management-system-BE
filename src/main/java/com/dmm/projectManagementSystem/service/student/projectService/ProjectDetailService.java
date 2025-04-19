package com.dmm.projectManagementSystem.service.student.projectService;

import com.dmm.projectManagementSystem.dto.ApiResponseStudent;
import com.dmm.projectManagementSystem.dto.project.CouncilResDTO;
import com.dmm.projectManagementSystem.dto.project.DefenseScheduleResDTO;
import com.dmm.projectManagementSystem.model.Evaluation;
import com.dmm.projectManagementSystem.model.Meeting;
import com.dmm.projectManagementSystem.model.Task;

import java.util.List;

public interface ProjectDetailService {
    ApiResponseStudent<DefenseScheduleResDTO> handleGetDefenseSchedule (Long topicId);

    ApiResponseStudent<CouncilResDTO> handleGetCouncil (Long topicId);

    ApiResponseStudent<List<Meeting>> handleGetMeeting (Long topicId, int page, int size, String sort);

    ApiResponseStudent<List<Evaluation>> handleGetEvaluation (Long topicId);

    ApiResponseStudent<List<Task>> handleGetTask (Long topicId, int page, int size, String sort);
}
