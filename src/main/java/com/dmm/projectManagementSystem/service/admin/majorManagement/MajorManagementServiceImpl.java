package com.dmm.projectManagementSystem.service.admin.majorManagement;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.department.DepartmentListByPageResponse;
import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.dmm.projectManagementSystem.dto.major.MajorListByPageResponse;
import com.dmm.projectManagementSystem.model.Department;
import com.dmm.projectManagementSystem.model.Major;
import com.dmm.projectManagementSystem.repo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MajorManagementServiceImpl implements MajorManagementService{

    final private MajorRepo majorRepo;
    final private UserRepo userRepo;
    final private TopicRepo topicRepo;
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
    public Pair<String, Boolean> deleteMajor(Long id) {

        if(majorRepo.existsById(id)) {
            Major major = majorRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Khong ton tai major voi id: " + id));
            if(userRepo.existsByMajor(major)) {
                return Pair.of("Still have students in major!", false);
            }
            if(topicRepo.existsByMajor(major)) {
                return Pair.of("Still have topic in major!", false);
            }
            majorRepo.deleteById(id);
            return Pair.of("Deleted!", true);
        }
        return Pair.of("Still have students in major!", false);
    }

    @Override
    public MajorListByPageResponse getAllMajor(String name, Long departmentID, int page, int limit) {
        Department department = null;
        if (departmentID != null) {
            department = departmentRepo.findById(departmentID)
                    .orElseThrow(() -> new RuntimeException("Khong tim thay department voi id: " + departmentID));
        }
        Page<CRUDMajor> crudMajorPage = majorRepo.findAllMajor(name, department, PageRequest.of(page, limit))
                .map(CRUDMajor::fromMajor);

        return MajorListByPageResponse.fromSplitPage(crudMajorPage.getContent(), crudMajorPage.getTotalPages(), page, limit);
    }
}
