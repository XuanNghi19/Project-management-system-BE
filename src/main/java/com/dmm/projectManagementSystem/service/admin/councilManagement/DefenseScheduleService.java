package com.dmm.projectManagementSystem.service.admin.councilManagement;

import com.dmm.projectManagementSystem.dto.defenseSchedule.CRUDDefenseSchedule;
import com.dmm.projectManagementSystem.model.Council;
import org.springframework.data.util.Pair;

public interface DefenseScheduleService {
    void addDefenseSchedule(Council council, CRUDDefenseSchedule requests) throws Exception;
    void updateDefenseSchedule(CRUDDefenseSchedule requests) throws Exception;
    void deleteDefenceSchedule(Long defenseScheduleID) throws Exception;
}
