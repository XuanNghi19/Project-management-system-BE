package com.dmm.projectManagementSystem.dto.major;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.model.Department;
import com.dmm.projectManagementSystem.model.Major;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CRUDMajor {
    Long id;
    String name;
    Department department;

    static public CRUDMajor fromMajor(Major major) {
        return CRUDMajor.builder()
                .id(major.getId())
                .name(major.getName())
                .department(major.getDepartment())
                .build();
    }
}
