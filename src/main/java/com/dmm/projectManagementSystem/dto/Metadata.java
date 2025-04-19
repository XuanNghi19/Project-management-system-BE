package com.dmm.projectManagementSystem.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Metadata {
    private int page;
    private int pageSize;
    private int totalPage;
    private Long totalElement;
}
