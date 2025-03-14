package com.dmm.projectManagementSystem.service.admin.departmentManagement;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.department.DepartmentListByPageResponse;

import java.util.List;

public interface DepartmentManagementService {
    boolean addDepartment(List<CRUDDepartment> cDepartmentList);
    boolean updateDepartment(CRUDDepartment uDepartment);
    boolean deleteDepartment(Long id);

    DepartmentListByPageResponse getAllDepartment(String name, int page, int limit);
}
