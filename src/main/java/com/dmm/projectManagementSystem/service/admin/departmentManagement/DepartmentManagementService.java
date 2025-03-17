package com.dmm.projectManagementSystem.service.admin.departmentManagement;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.department.DepartmentListByPageResponse;
import org.springframework.data.util.Pair;

import java.util.List;

public interface DepartmentManagementService {
    boolean addDepartment(List<CRUDDepartment> cDepartmentList);
    boolean updateDepartment(CRUDDepartment uDepartment);
    Pair<String, Boolean> deleteDepartment(Long id);

    DepartmentListByPageResponse getAllDepartment(String name, int page, int limit);
}
