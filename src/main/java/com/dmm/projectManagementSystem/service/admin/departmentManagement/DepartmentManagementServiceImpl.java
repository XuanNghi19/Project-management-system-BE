package com.dmm.projectManagementSystem.service.admin.departmentManagement;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.department.DepartmentListByPageResponse;
import com.dmm.projectManagementSystem.model.Department;
import com.dmm.projectManagementSystem.repo.DepartmentRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentManagementServiceImpl implements DepartmentManagementService{

    final private DepartmentRepo departmentRepo;

    @Transactional
    @Override
    public boolean addDepartment(List<CRUDDepartment> cDepartmentList) {
        try {
            for (var x : cDepartmentList) {
                departmentRepo.save(Department.fromCRUDDepartment(x));
            }
            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            return false;
        }
    }

    @Override
    public boolean updateDepartment(CRUDDepartment uDepartment) {
        try {
            departmentRepo.save(Department.fromCRUDDepartment(uDepartment));
            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            return false;
        }
    }

    @Override
    public boolean deleteDepartment(Long id) {
        if(departmentRepo.existsById(id)) {
            departmentRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public DepartmentListByPageResponse getAllDepartment(String name, int page, int limit) {
        Page<CRUDDepartment> crudDepartmentPage = departmentRepo.findAllDepartment(name, PageRequest.of(page, limit))
                .map(CRUDDepartment::fromDepartment);

        return DepartmentListByPageResponse.fromSplitPage(crudDepartmentPage.getContent(), crudDepartmentPage.getTotalPages(), page, limit);
    }
}
