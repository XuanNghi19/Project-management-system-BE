package com.dmm.projectManagementSystem.service.admin.councilManagement;

import com.dmm.projectManagementSystem.dto.council.CouncilDetailResponse;
import com.dmm.projectManagementSystem.dto.council.CouncilListByPageResponse;
import com.dmm.projectManagementSystem.dto.council.CreateCouncilRequest;
import com.dmm.projectManagementSystem.dto.council.UpdateCouncilRequest;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;

public interface CouncilManagementService {
    Pair<String, Boolean> addCouncil(CreateCouncilRequest request) throws Exception;

    Pair<String, Boolean> updateCouncil(UpdateCouncilRequest request) throws Exception;

    Pair<String, Boolean> deleteCouncil(Long councilID) throws Exception;

    CouncilListByPageResponse getAllCouncil(
            String name,
            Long topicSemesterID,
            Long departmentID,
            LocalDateTime startTime,
            LocalDateTime endTime,
            int page,
            int limit
    ) throws Exception;

    CouncilDetailResponse getCouncilDetail(Long councilID) throws Exception;
}
