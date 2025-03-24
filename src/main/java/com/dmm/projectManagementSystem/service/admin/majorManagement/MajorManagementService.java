package com.dmm.projectManagementSystem.service.admin.majorManagement;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.department.DepartmentListByPageResponse;
import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.dmm.projectManagementSystem.dto.major.MajorListByPageResponse;
import com.dmm.projectManagementSystem.model.Department;
import org.springframework.data.util.Pair;

import java.util.List;

public interface MajorManagementService {
    boolean addMajor(List<CRUDMajor> cMajorList);
    boolean updateMajor(CRUDMajor uMajor);
    Pair<String, Boolean> deleteMajor(Long id);

    MajorListByPageResponse getAllMajor(String name, Long departmentID, int page, int limit);
}
