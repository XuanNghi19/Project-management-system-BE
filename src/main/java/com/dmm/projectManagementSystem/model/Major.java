package com.dmm.projectManagementSystem.model;

import com.dmm.projectManagementSystem.dto.department.CRUDDepartment;
import com.dmm.projectManagementSystem.dto.major.CRUDMajor;
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

    @Column(name = "progress_percentage")
    private int progressPercentage;

    @Column(name = "report_percentage")
    private int reportPercentage;

    @Column(name = "defense_percentage")
    private int defensePercentage;

    @Column(name = "review_percentage")
    private int reviewPercentage;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    static public Major fromCRUDMajor(CRUDMajor crudMajor) {
        return Major.builder()
                .id(crudMajor.getId())
                .name(crudMajor.getName())
                .progressPercentage(crudMajor.getProgressPercentage())
                .reportPercentage(crudMajor.getReportPercentage())
                .defensePercentage(crudMajor.getDefensePercentage())
                .reviewPercentage(crudMajor.getReviewPercentage())
                .department(Department.fromCRUDDepartment(crudMajor.getDepartment()))
                .build();
    }
}

