package com.dmm.projectManagementSystem.dto.project;

import com.dmm.projectManagementSystem.model.DefenseSchedule;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class DefenseScheduleResDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String note;

    public DefenseScheduleResDTO convertToDefenseScheduleDTO (DefenseSchedule defenseSchedule){
       this.startTime = defenseSchedule.getStartTime();
       this.endTime = defenseSchedule.getEndTime();
       this.location = defenseSchedule.getLocation();
       this.note = defenseSchedule.getNote();
        return this;
    }
}
