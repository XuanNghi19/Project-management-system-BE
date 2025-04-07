package com.dmm.projectManagementSystem.service.admin.councilManagement;

import com.dmm.projectManagementSystem.dto.defenseSchedule.CRUDDefenseSchedule;
import com.dmm.projectManagementSystem.model.Council;
import com.dmm.projectManagementSystem.model.DefenseSchedule;
import com.dmm.projectManagementSystem.model.Topic;
import com.dmm.projectManagementSystem.repo.DefenseScheduleRepo;
import com.dmm.projectManagementSystem.repo.TopicRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefenseScheduleServiceImpl implements DefenseScheduleService {

    final private DefenseScheduleRepo defenseScheduleRepo;
    final private TopicRepo topicRepo;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void addDefenseSchedule(Council council, CRUDDefenseSchedule request) throws Exception {
        try {
            Topic exTopic = topicRepo.findByIdNum(request.getTopic().getIdNum());
            DefenseSchedule defenseSchedule = DefenseSchedule.fromCRUDDefenseSchedule(request, council, exTopic);
            defenseSchedule.setId(null);
            defenseScheduleRepo.save(defenseSchedule);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void updateDefenseSchedule(CRUDDefenseSchedule request) throws Exception {
        try {
            Topic exTopic = topicRepo.findByIdNum(request.getTopic().getIdNum());

            DefenseSchedule exDefenseSchedule = defenseScheduleRepo.findById(request.getId())
                    .orElseThrow(() -> new Exception("Khong tim thay defenseSchedule voi id: " + request.getId()));

            defenseScheduleRepo.save(DefenseSchedule.fromCRUDDefenseSchedule(request, exDefenseSchedule.getCouncil(), exTopic));
        } catch (Exception e) {
            throw new Exception(e);
        }

    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void deleteDefenceSchedule(Long defenseScheduleID) throws Exception {
        if (defenseScheduleRepo.existsById(defenseScheduleID)) {
            try {
                defenseScheduleRepo.deleteById(defenseScheduleID);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }
}
