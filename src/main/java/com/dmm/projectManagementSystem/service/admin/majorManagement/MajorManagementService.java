package com.dmm.projectManagementSystem.service.admin.majorManagement;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.department.DepartmentListByPageResponse;
import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.dmm.projectManagementSystem.dto.major.MajorListByPageResponse;
import com.dmm.projectManagementSystem.model.Department;

import java.util.List;

public interface MajorManagementService {
    boolean addMajor(List<CRUDMajor> cMajorList);
    boolean updateMajor(CRUDMajor uMajor);
    boolean deleteMajor(Long id);

    MajorListByPageResponse getAllMajor(String name, Department department, int page, int limit);
}
