package com.dmm.projectManagementSystem.service.admin.majorManagement;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.department.DepartmentListByPageResponse;
import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.dmm.projectManagementSystem.dto.major.MajorListByPageResponse;
import com.dmm.projectManagementSystem.model.Department;
import com.dmm.projectManagementSystem.model.Major;
import com.dmm.projectManagementSystem.repo.DepartmentRepo;
import com.dmm.projectManagementSystem.repo.MajorRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MajorManagementServiceImpl implements MajorManagementService{

    final private MajorRepo majorRepo;
    final private DepartmentRepo departmentRepo;

    @Transactional
    @Override
    public boolean addMajor(List<CRUDMajor> cMajorList) {
        try {
            for (var x : cMajorList) {
                majorRepo.save(Major.fromCRUDMajor(x));
            }
            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            return false;
        }
    }

    @Transactional
    @Override
    public boolean updateMajor(CRUDMajor uMajor) {
        try {
            majorRepo.save(Major.fromCRUDMajor(uMajor));
            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            return false;
        }
    }

    @Transactional
    @Override
    public boolean deleteMajor(Long id) {
        if(majorRepo.existsById(id)) {
            majorRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public MajorListByPageResponse getAllMajor(String name, Department department, int page, int limit) {
        Page<CRUDMajor> crudMajorPage = majorRepo.findAllMajor(name, department, PageRequest.of(page, limit))
                .map(CRUDMajor::fromMajor);

        return MajorListByPageResponse.fromSplitPage(crudMajorPage.getContent(), crudMajorPage.getTotalPages(), page, limit);
    }
}
