package com.dmm.projectManagementSystem.dto.department;

import com.dmm.projectManagementSystem.model.Department;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CRUDDepartment {
    Long id;
    String name;
    String description;

    static public CRUDDepartment fromDepartment(Department department) {
        return CRUDDepartment.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .build();
    }
}
