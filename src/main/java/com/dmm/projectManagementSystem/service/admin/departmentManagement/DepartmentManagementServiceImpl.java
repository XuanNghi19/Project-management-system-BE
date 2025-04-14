package com.dmm.projectManagementSystem.service.admin.departmentManagement;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.department.DepartmentListByPageResponse;
import com.dmm.projectManagementSystem.model.Department;
import com.dmm.projectManagementSystem.repo.CouncilRepo;
import com.dmm.projectManagementSystem.repo.DepartmentRepo;
import com.dmm.projectManagementSystem.repo.MajorRepo;
import com.dmm.projectManagementSystem.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentManagementServiceImpl implements DepartmentManagementService{

    final private DepartmentRepo departmentRepo;
    final private MajorRepo majorRepo;
    final private UserRepo userRepo;
    final private CouncilRepo councilRepo;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean addDepartment(List<CRUDDepartment> cDepartmentList) throws Exception {
        try {
            for (var x : cDepartmentList) {
                Department newDepartment = Department.fromCRUDDepartment(x);
                newDepartment.setId(null);
                departmentRepo.save(newDepartment);
            }
            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            throw new Exception(ex);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean updateDepartment(CRUDDepartment uDepartment) throws Exception {
        try {
            departmentRepo.save(Department.fromCRUDDepartment(uDepartment));
            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            throw new Exception(ex);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Pair<String, Boolean> deleteDepartment(Long id) throws Exception {
        if(departmentRepo.existsById(id)) {
            Department department = departmentRepo.findById(id)
                    .orElseThrow(Exception::new);
            if(majorRepo.existsByDepartment(department) || userRepo.existsByDepartment(department) || councilRepo.existsByDepartment(department)) {
               return Pair.of("still exists in the department!", false);
            }
            try {
                departmentRepo.deleteById(id);
                return Pair.of("Deleted!", true);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex);
                throw new Exception(ex);
            }
        }
        return Pair.of(String.format("department with id does not exist: %d", id), false);
    }

    @Override
    public DepartmentListByPageResponse getAllDepartment(String name, int page, int limit) {
        Page<CRUDDepartment> crudDepartmentPage = departmentRepo.findAllDepartment(name, PageRequest.of(page, limit))
                .map(CRUDDepartment::fromDepartment);

        return DepartmentListByPageResponse.fromSplitPage(crudDepartmentPage.getContent(), crudDepartmentPage.getTotalPages(), page, limit);
    }
}
