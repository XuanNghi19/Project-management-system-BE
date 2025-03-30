package com.dmm.projectManagementSystem.service.admin.majorManagement;

import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.dmm.projectManagementSystem.dto.major.MajorListByPageResponse;
import org.springframework.data.util.Pair;

import java.util.List;

public interface MajorManagementService {
    boolean addMajor(List<CRUDMajor> cMajorList) throws Exception;
    boolean updateMajor(CRUDMajor uMajor) throws Exception;
    Pair<String, Boolean> deleteMajor(Long id) throws Exception;

    MajorListByPageResponse getAllMajor(String name, Long departmentID, int page, int limit);
}
