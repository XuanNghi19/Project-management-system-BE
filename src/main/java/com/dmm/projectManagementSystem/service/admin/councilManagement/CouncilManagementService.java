package com.dmm.projectManagementSystem.service.admin.councilManagement;

import com.dmm.projectManagementSystem.dto.council.CouncilDetailResponse;
import com.dmm.projectManagementSystem.dto.council.CouncilListByPageResponse;
import com.dmm.projectManagementSystem.dto.council.CreateCouncilRequest;
import com.dmm.projectManagementSystem.dto.council.UpdateCouncilRequest;
import com.dmm.projectManagementSystem.dto.defenseSchedule.CRUDDefenseSchedule;
import org.springframework.data.util.Pair;

import java.util.List;

public interface CouncilManagementService {
    Pair<String, Boolean> addCouncil(CreateCouncilRequest request) throws Exception;
    Pair<String, Boolean> updateCouncil(UpdateCouncilRequest request) throws Exception;
    Pair<String, Boolean> deleteCouncil(Long councilID) throws Exception;

    Pair<String, Boolean> addDefenseSchedule(Long councilID, List<CRUDDefenseSchedule> requests) throws Exception;
    Pair<String, Boolean> updateDefenseSchedule(Long councilID, CRUDDefenseSchedule request) throws Exception;
    Pair<String, Boolean> deleteDefenceSchedule(Long councilID, Long defenseScheduleID) throws Exception;

    CouncilListByPageResponse getAllCouncil(String name, Long courseID, Long departmentID, int page, int limit);
    CouncilDetailResponse getCouncilDetail(Long councilID);
}
