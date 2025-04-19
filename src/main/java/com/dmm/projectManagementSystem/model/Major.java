package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "major")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "department_id", nullable = true)
    private Department department;

    static public Major fromCRUDMajor(CRUDMajor crudMajor) {
        return Major.builder()
                .name(crudMajor.getName())
                .department(crudMajor.getDepartment())
                .build();
    }
}

