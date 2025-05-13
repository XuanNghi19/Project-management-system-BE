package com.dmm.projectManagementSystem.dto.grade;

import lombok.Data;

@Data
public class UpdateGradeDTO {
    private Long id;
    private Double score;
    private String type;
}
