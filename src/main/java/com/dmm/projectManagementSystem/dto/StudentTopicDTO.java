package com.dmm.projectManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentTopicDTO {
    private Long id;
    private String name;
    private String dob;
    private boolean status;
}
