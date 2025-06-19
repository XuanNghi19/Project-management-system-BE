package com.dmm.projectManagementSystem.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestResponse <T>{
    private T data;

    private String message;
    private Object error;
    @Builder.Default
    private int statusCode = 1000;
}
