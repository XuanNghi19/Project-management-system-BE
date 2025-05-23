package com.dmm.projectManagementSystem.service.admin.majorManagement;

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
    final private TeamRepo teamRepo;
    final private ClassTopicRepo classTopicRepo;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean addMajor(List<CRUDMajor> cMajorList) throws Exception{
        try {
            for (var x : cMajorList) {
                Major newMajor = Major.fromCRUDMajor(x);
                newMajor.setId(null);
                majorRepo.save(newMajor);
            }
            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            throw new Exception(ex);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean updateMajor(CRUDMajor uMajor) throws Exception{
        try {
            majorRepo.save(Major.fromCRUDMajor(uMajor));
            return true;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            throw new Exception(ex);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Pair<String, Boolean> deleteMajor(Long id) throws Exception {

        if(majorRepo.existsById(id)) {
            Major major = majorRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Khong ton tai major voi id: " + id));
            if(userRepo.existsByMajor(major)) {
                return Pair.of("Still have students in major!", false);
            }
            if(topicRepo.existsByMajor(major)) {
                return Pair.of("Still have topic in major!", false);
            }
            if(teamRepo.existsByMajor(major)) {
                return Pair.of("Still have team in major!", false);
            }
            if(classTopicRepo.existsByMajor(major)) {
                return Pair.of("Still have class topic in major!", false);
            }
            try {
                majorRepo.delete(major);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex);
                throw new Exception("Can't delete major!");
            }
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
        Page<CRUDMajor> crudMajorPage = majorRepo.findAllMajor(name, departmentID, PageRequest.of(page, limit))
                .map(CRUDMajor::fromMajor);

        return MajorListByPageResponse.fromSplitPage(crudMajorPage.getContent(), crudMajorPage.getTotalPages(), page, limit);
    }
}
