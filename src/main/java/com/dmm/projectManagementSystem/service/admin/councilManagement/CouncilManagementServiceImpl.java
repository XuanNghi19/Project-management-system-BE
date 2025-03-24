package com.dmm.projectManagementSystem.service.admin.councilManagement;

import com.dmm.projectManagementSystem.dto.council.CouncilDetailResponse;
import com.dmm.projectManagementSystem.dto.council.CouncilListByPageResponse;
import com.dmm.projectManagementSystem.dto.council.CreateCouncilRequest;
import com.dmm.projectManagementSystem.dto.council.UpdateCouncilRequest;
import com.dmm.projectManagementSystem.dto.defenseSchedule.CRUDDefenseSchedule;
import com.dmm.projectManagementSystem.model.*;
import com.dmm.projectManagementSystem.repo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouncilManagementServiceImpl implements CouncilManagementService{

    final private CouncilRepo councilRepo;
    final private DefenseScheduleRepo defenseScheduleRepo;
    final private CourseRepo courseRepo;
    final private DepartmentRepo departmentRepo;
    final private TopicRepo topicRepo;

    @Transactional
    @Override
    public Pair<String, Boolean> addCouncil(CreateCouncilRequest request) throws Exception {
        Course exCourse = courseRepo.findById(request.getCourseID())
                .orElseThrow(() -> new RuntimeException("Khong tim thay course voi id: " + request.getCourseID()));
        Department exDepartment = departmentRepo.findById(request.getDepartmentID())
                .orElseThrow(() -> new RuntimeException("Khong tim thay department voi id: " + request.getDepartmentID()));

        Council council = Council.fromCreateCouncilRequest(request, exCourse, exDepartment);
        council = councilRepo.save(council);

        for(var x : request.getScheduleRequests()) {

            Topic exTopic = topicRepo.findByIdNum(x.getTopicIdNum());

            DefenseSchedule defenseSchedule = DefenseSchedule.fromCRUDDefenseSchedule(x, council, exTopic);
            defenseScheduleRepo.save(defenseSchedule);
        }

        return Pair.of("Add Council Successes!", true);
    }

    @Transactional
    @Override
    public Pair<String, Boolean> updateCouncil(UpdateCouncilRequest request) throws Exception {

        Course exCourse = courseRepo.findById(request.getCourseID())
                .orElseThrow(() -> new RuntimeException("Khong tim thay course voi id: " + request.getCourseID()));
        Department exDepartment = departmentRepo.findById(request.getDepartmentID())
                .orElseThrow(() -> new RuntimeException("Khong tim thay department voi id: " + request.getDepartmentID()));

        Council updateCouncil = councilRepo.save(Council.fromUpdateCouncilRequest(request, exCourse, exDepartment));

        List<DefenseSchedule> defenseScheduleList = defenseScheduleRepo.findAllByCouncil(updateCouncil);

        for (var x : defenseScheduleList) {
            x.setLocation(updateCouncil.getLocation());
            defenseScheduleRepo.save(x);
        }

        return Pair.of("Update Council Successes!", true);
    }

    @Transactional
    @Override
    public Pair<String, Boolean> deleteCouncil(Long councilID) throws Exception {

        if(councilRepo.existsById(councilID)) {
            Council council = councilRepo.findById(councilID)
                    .orElseThrow(() -> new RuntimeException("Khong tim thay council voi id: " + councilID));

            if(defenseScheduleRepo.existsByCouncil(council)) {
                if(defenseScheduleRepo.deleteAllByCouncil(council) <= 0) {
                       return Pair.of("Delete Defense Schedule Fail!", false);
                }
            }

            councilRepo.deleteById(councilID);
            return Pair.of("Delete Council Successes!", true);
        }

        return Pair.of("Delete Council Fail!", false);
    }

    @Transactional
    @Override
    public Pair<String, Boolean> addDefenseSchedule(Long councilID, List<CRUDDefenseSchedule> requests) throws Exception {
        Council council = councilRepo.findById(councilID)
                .orElseThrow(() -> new RuntimeException("Khong tim thay council voi id: " + councilID));

        for (var x : requests) {
            Topic exTopic = topicRepo.findByIdNum(x.getTopicIdNum());
            DefenseSchedule defenseSchedule = DefenseSchedule.fromCRUDDefenseSchedule(x, council, exTopic);
            defenseScheduleRepo.save(defenseSchedule);
        }
        return Pair.of("Add Defense Schedule Successes!", true);
    }

    @Transactional
    @Override
    public Pair<String, Boolean> updateDefenseSchedule(Long councilID, CRUDDefenseSchedule request) throws Exception {
        Council council = councilRepo.findById(councilID)
                .orElseThrow(() -> new RuntimeException("Khong tim thay council voi id: " + councilID));

        return null;
    }

    @Transactional
    @Override
    public Pair<String, Boolean> deleteDefenceSchedule(Long councilID, Long defenseScheduleID) throws Exception{
        return null;
    }

    @Transactional
    @Override
    public CouncilListByPageResponse getAllCouncil(String name, Long courseID, Long departmentID, int page, int limit) {
        return null;
    }

    @Transactional
    @Override
    public CouncilDetailResponse getCouncilDetail(Long councilID) {
        return null;
    }
}
