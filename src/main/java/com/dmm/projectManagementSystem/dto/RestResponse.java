package com.dmm.projectManagementSystem.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RestResponse <T>{
    private T data;
    private Metadata metadata;
    private String message;
    private Object error;
    @Builder.Default
    private int statusCode = 1000;
}
