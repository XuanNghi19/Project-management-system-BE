package com.dmm.projectManagementSystem.service.admin.departmentManagement;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.department.DepartmentListByPageResponse;
import org.springframework.data.util.Pair;

import java.util.List;

public interface DepartmentManagementService {
    boolean addDepartment(List<CRUDDepartment> cDepartmentList) throws Exception;
    boolean updateDepartment(CRUDDepartment uDepartment) throws Exception;
    Pair<String, Boolean> deleteDepartment(Long id) throws Exception;

    DepartmentListByPageResponse getAllDepartment(String name, int page, int limit);
}
