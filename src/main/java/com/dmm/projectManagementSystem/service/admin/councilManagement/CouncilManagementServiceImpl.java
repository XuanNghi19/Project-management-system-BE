package com.dmm.projectManagementSystem.service.admin.councilManagement;

import com.dmm.projectManagementSystem.dto.council.*;
import com.dmm.projectManagementSystem.dto.defenseSchedule.CRUDDefenseSchedule;
import com.dmm.projectManagementSystem.enums.ActionTypes;
import com.dmm.projectManagementSystem.model.*;
import com.dmm.projectManagementSystem.repo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouncilManagementServiceImpl implements CouncilManagementService{

    final private CouncilRepo councilRepo;
    final private DefenseScheduleRepo defenseScheduleRepo;
    final private TopicSemesterRepo topicSemesterRepo;
    final private DepartmentRepo departmentRepo;
    final private DefenseScheduleService defenseScheduleService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Pair<String, Boolean> addCouncil(CreateCouncilRequest request) throws Exception {
        TopicSemester exTopicSemester = topicSemesterRepo.findById(request.getTopicSemesterID())
                .orElseThrow(() -> new Exception("Khong tim thay course voi id: " + request.getTopicSemesterID()));
        Department exDepartment = departmentRepo.findById(request.getDepartmentID())
                .orElseThrow(() -> new Exception("Khong tim thay department voi id: " + request.getDepartmentID()));

        Council council = Council.fromCreateCouncilRequest(request, exTopicSemester, exDepartment);

        // try cho rollback
        try {
            council = councilRepo.save(council);

            for(var x : request.getScheduleRequests()) {
                defenseScheduleService.addDefenseSchedule(council, x);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return Pair.of("Add Council Successes!", true);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Pair<String, Boolean> updateCouncil(UpdateCouncilRequest request) throws Exception {

        TopicSemester exTopicSemester = topicSemesterRepo.findById(request.getCourseID())
                .orElseThrow(() -> new Exception("Khong tim thay course voi id: " + request.getCourseID()));
        Department exDepartment = departmentRepo.findById(request.getDepartmentID())
                .orElseThrow(() -> new Exception("Khong tim thay department voi id: " + request.getDepartmentID()));

        try {
            Council updateCouncil = councilRepo.save(Council.fromUpdateCouncilRequest(request, exTopicSemester, exDepartment));

            for (var x : request.getScheduleRequests()) {
                if (x.getAction() == ActionTypes.DELETE) {
                    defenseScheduleService.deleteDefenceSchedule(x.getId());
                } else if (x.getAction() == ActionTypes.UPDATE) {
                    defenseScheduleService.updateDefenseSchedule(x);
                } else if (x.getAction() == ActionTypes.CREATE) {
                    defenseScheduleService.addDefenseSchedule(updateCouncil, x);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Pair.of("Update Council Successes!", true);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Pair<String, Boolean> deleteCouncil(Long councilID) throws Exception {

        if(councilRepo.existsById(councilID)) {
            Council council = councilRepo.findById(councilID)
                    .orElseThrow(() -> new Exception("Khong tim thay council voi id: " + councilID));

            if(defenseScheduleRepo.existsByCouncil(council)) {
                try {
                    defenseScheduleRepo.deleteAllByCouncil(council);
                    councilRepo.deleteById(councilID);
                    return Pair.of("Delete Council Successes!", true);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return Pair.of("Delete Council Fail!", false);
    }

    @Override
    public CouncilListByPageResponse getAllCouncil (
            String name,
            Long topicSemesterID,
            Long departmentID,
            LocalDateTime startTime,
            LocalDateTime endTime,
            int page,
            int limit
    ) throws Exception
    {
        TopicSemester topicSemester = null;
        Department department = null;

        if(topicSemesterID != null) {
            topicSemester = topicSemesterRepo.findById(topicSemesterID)
                    .orElseThrow(() -> new Exception("Khong tim thay topic semester voi id: " + topicSemesterID));
        }
        if(departmentID != null) {
            department = departmentRepo.findById(departmentID)
                    .orElseThrow(() -> new Exception("Khong tim thay department voi id: " + departmentID));
        }

        Page<CouncilResponse> councilPage = councilRepo.findAllCouncil(
                        name,
                        topicSemester,
                        department,
                        startTime,
                        endTime,
                        PageRequest.of(page, limit)
                )
                .map(CouncilResponse::fromCouncil);

        return CouncilListByPageResponse.fromSplitPage(
                councilPage.getContent(),
                councilPage.getTotalPages(),
                page,
                limit
        );
    }

    @Override
    public CouncilDetailResponse getCouncilDetail(Long councilID) throws Exception {
        Council exCouncil = councilRepo.findById(councilID)
                .orElseThrow(() -> new Exception("Khong tim thay council voi id: " + councilID));

        List<CRUDDefenseSchedule> defenseScheduleList = defenseScheduleRepo.findAllByCouncil(exCouncil).stream().map(
                CRUDDefenseSchedule::fromDefenseSchedule
        ).toList();

        return CouncilDetailResponse.fromCouncil(exCouncil, defenseScheduleList);
    }
}
