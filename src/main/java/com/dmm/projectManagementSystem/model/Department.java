package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "department")
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    static public Department fromCRUDDepartment(CRUDDepartment crudDepartment) {
        return Department.builder()
                .id(crudDepartment.getId())
                .name(crudDepartment.getName())
                .description(crudDepartment.getDescription())
                .build();
    }
}
